package com.emoba.ballbuster;

import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        AimFragment.OnFragmentInteractionListener,
        TouchFragment.OnFragmentInteractionListener,
        SensorFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_aim:
                    openAimView();
                    return true;
                case R.id.navigation_touch:
                    openTouchView();
                    return true;
                case R.id.navigation_sensor:
                    openSensorView();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        openAimView();
    }

    public void openAimView() {
        AimFragment fragment = new AimFragment();
        setFragment(fragment);
    }

    public void openTouchView() {
        TouchFragment fragment = new TouchFragment();
        setFragment(fragment);
    }

    public void openSensorView() {
        SensorFragment fragment = new SensorFragment();
        setFragment(fragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(String title) {
        Log.d("bla", title);
        getSupportActionBar().setTitle(title);
    }
}
