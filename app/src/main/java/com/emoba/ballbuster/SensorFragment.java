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

import com.emoba.ballbuster.View.Control;
import com.emoba.ballbuster.View.SensorView;

import java.util.Arrays;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_MAGNETIC_FIELD;

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


    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];
    private float[] actualOrientationAngles = new float[3];
    private Sensor sensorAcce;
    private Sensor sensorMag;

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
        sensorAcce = mSensorManager.getDefaultSensor(TYPE_ACCELEROMETER);
        sensorMag = mSensorManager.getDefaultSensor(TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,sensorAcce , SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
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

        if (sensorEvent.sensor.getType() == TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, mAccelerometerReading,0, mAccelerometerReading.length);
        }
        else if (sensorEvent.sensor.getType() == TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, mMagnetometerReading,0, mMagnetometerReading.length);
        }

        mSensorManager.getRotationMatrix(mRotationMatrix, null, mAccelerometerReading, mMagnetometerReading);

        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

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

        float newx = (width / 2) + width * actualOrientationAngles[2];
        float newy = (height / 2) - height * actualOrientationAngles[1];

        sensorView.setNewPosition((int)newx,(int)newy);
    }

    private void sendToBall() {
        float headingvalue = 0;
        float velocityvalue = 0;

        MainActivity activity = (MainActivity) getActivity();
        Handler ballHandler = activity.getBallHandler();

        Message msg= ballHandler.obtainMessage();
        msg.what = TheBallControllerThread.BALL_CRUSE;
        Bundle content = new Bundle();

        Control controller = this.sensorView.getController();
        if (controller != null) {
            headingvalue = controller.getAngleOfPointOnCircle();
            velocityvalue = controller.getDistanceFromMiddleLine();
        }

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
