package com.emoba.ballbuster;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.emoba.ballbuster.View.SensorView;

import java.util.Arrays;

import static android.hardware.Sensor.TYPE_GAME_ROTATION_VECTOR;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SensorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorFragment extends Fragment implements SensorEventListener {


    private OnFragmentInteractionListener mListener;
    private SensorView sensorView;

    private SensorManager mSensorManager;
    private Sensor gamesensor;

    private final float[] mOrientationAngles = new float[3];
    private final float[] actualOrientationAngles = new float[3];


    public SensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorFragment newInstance(String param1, String param2) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        gamesensor = mSensorManager.getDefaultSensor(TYPE_GAME_ROTATION_VECTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, gamesensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    public void onPause() {
        super.onPause();
        // Don't receive any more updates from either sensor.
        mSensorManager.unregisterListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mListener != null) {
            mListener.onFragmentInteraction("Sensor");
        }

        View view = inflater.inflate(R.layout.fragment_sensor, container, false);

        RelativeLayout controllerLayout = (RelativeLayout) view.findViewById(R.id.sensorControlLayout);
        sensorView = new SensorView(getActivity());
        controllerLayout.addView(sensorView);

        return view;
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



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == TYPE_GAME_ROTATION_VECTOR) {
            System.arraycopy(sensorEvent.values, 0, mOrientationAngles,0, mOrientationAngles.length);
        }


        if (!Arrays.equals(mOrientationAngles, actualOrientationAngles)) {
            for (int i = 0; i < mOrientationAngles.length; i++) {
                actualOrientationAngles[i] = mOrientationAngles[i];
            }
            orientationAnglesChanged();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void orientationAnglesChanged() {
//        Log.d("Sensor - orientation", Arrays.toString(mOrientationAngles));
        updateview();
        sendToBall();
    }

    public void updateview() {
        int width = sensorView.getWidth();
        int height = sensorView.getHeight();

        Log.d("game", "updateview: "+actualOrientationAngles[0]+" "+actualOrientationAngles[1]+" "+actualOrientationAngles[2]);

        float newx = (width / 2) + width * actualOrientationAngles[1];
        float newy = (height / 2) + height * actualOrientationAngles[0];

        sensorView.setNewPosition((int)newx,(int)newy);
    }

    private void sendToBall() {
        MainActivity activity = (MainActivity) getActivity();
        Handler ballHandler = activity.getBallHandler();

        Message msg= ballHandler.obtainMessage();

        msg.what = TheBallControllerThread.BALL_CRUSE;

        Bundle content = new Bundle();
        //TODO: calculate meanifull values
        float headingvalue = mOrientationAngles[2];
        float velocityvalue = mOrientationAngles[1];

        content.putFloat(TheBallControllerThread.HEADING, headingvalue);
        content.putFloat(TheBallControllerThread.VELOCITY, velocityvalue);

        msg.setData(content);

        msg.sendToTarget();
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
        void onFragmentInteraction(String title);
    }
}
