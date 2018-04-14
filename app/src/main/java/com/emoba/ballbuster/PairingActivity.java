package com.emoba.ballbuster;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    TextView textView;
    SpheroRobotProxy proxy;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        textView = findViewById(R.id.textView);
        textView.setText("Checking permission...");

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (this.checkSelfPermission(ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestCoarsePermission();
        }
        if(this.checkSelfPermission(BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED){
            requestBltAdminPermission();

        }
        if(this.checkSelfPermission(BLUETOOTH) != PackageManager.PERMISSION_GRANTED){
            requestBltPermission();

        }
        if(bluetoothAdapter.isEnabled()){
            Button btn = findViewById(R.id.button);
            btn.setVisibility(View.GONE);
        }

        startDiscovery();

    }

    private void requestBltAdminPermission() {
        textView.setText("Request permission...");
        ActivityCompat.requestPermissions(this, new String[]{BLUETOOTH_ADMIN},2);
    }

    private void requestBltPermission() {
        textView.setText("Request permission...");
        ActivityCompat.requestPermissions(this, new String[]{BLUETOOTH},3);
    }

    private void requestCoarsePermission() {
        textView.setText("Request permission...");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

    }

    private void startDiscovery() {
        if(!bluetoothAdapter.isEnabled()){
            textView.setText("Bluetooth is disabled");

        }else {
            textView.setText("Discovering for Sphero Device...");
            boolean onEmulator = Build.PRODUCT.startsWith("sdk");
            proxy = SpheroRobotFactory.createRobot(onEmulator);
            proxy.setDiscoveryListener(this);
            proxy.startDiscovering(getApplicationContext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification notification) {

        textView.setText(notification.name());

        if (notification == SpheroRobotBluetoothNotification.Online) {

            textView.setText("Connected to ball...");
            Log.i("Sphero", "bluetooth connected");

            startMainActivity();

            proxy.stopDiscovering();
        }

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        ActivityCompat.finishAffinity(this);
    }

    private void restartAimActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    public void turnOnBluetooth(View view) {
        if(!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
        restartAimActivity();
    }
}
