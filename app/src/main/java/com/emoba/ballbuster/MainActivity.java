package com.emoba.ballbuster;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_aim);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_touch);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_sensor);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
        fragmentTransaction.add(R.id.sensor, fragment);
        fragmentTransaction.commit();
    }


}
