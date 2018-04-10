package com.emoba.ballbuster;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.emoba.ballbuster.View.Control;
import com.emoba.ballbuster.View.TouchView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TouchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TouchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TouchFragment extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Add View
    private TouchView touchView;

    private OnFragmentInteractionListener mListener;

    public TouchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TouchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TouchFragment newInstance(String param1, String param2) {
        TouchFragment fragment = new TouchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Touch");
        }
        View view = inflater.inflate(R.layout.fragment_touch, container, false);

        RelativeLayout controllerLayout = (RelativeLayout) view.findViewById(R.id.touchControlLayout);
        touchView = new TouchView(getActivity());
        controllerLayout.addView(touchView);
        touchView.setOnTouchListener(this);

        return view;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchView.setNewPosition((int)event.getX(), (int)event.getY());

        sendToBall();
        return true;
    }


    private void sendToBall() {
        float headingvalue = 0;
        float velocityvalue = 0;

        MainActivity activity = (MainActivity) getActivity();
        Handler ballHandler = activity.getBallHandler();

        Message msg= ballHandler.obtainMessage();
        msg.what = TheBallControllerThread.BALL_CRUSE;
        Bundle content = new Bundle();

        Control controller = this.touchView.getController();
        if (controller != null) {
            headingvalue = controller.getAngleOfPointOnCircle();
            velocityvalue = controller.getDistanceFromMiddleLine();
        }

        content.putFloat(TheBallControllerThread.HEADING, headingvalue);
        content.putFloat(TheBallControllerThread.VELOCITY, velocityvalue);

        msg.setData(content);
        msg.sendToTarget();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String title);
    }
}
