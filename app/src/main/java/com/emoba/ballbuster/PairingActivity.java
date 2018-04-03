package com.emoba.ballbuster;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

import ch.fhnw.edu.emoba.spherolib.SpheroRobotDiscoveryListener;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotFactory;
import ch.fhnw.edu.emoba.spherolib.SpheroRobotProxy;

public class PairingActivity extends AppCompatActivity implements SpheroRobotDiscoveryListener {

    TextView textView;
    SpheroRobotProxy proxy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);
        textView = findViewById(R.id.textView);
        textView.setText("Discovering for Sphero Device...");

        boolean onEmulator = Build.PRODUCT.startsWith("sdk");
        proxy = SpheroRobotFactory.createRobot(onEmulator);
        proxy.setDiscoveryListener(this);
        proxy.startDiscovering(getApplicationContext());
    }


    @Override
    public void handleRobotChangedState(SpheroRobotBluetoothNotification notification) {
        /*proxy.stopDiscovering();

        if (notification == SpheroRobotBluetoothNotification.Online) {

            Log.i("Sphero", "BlueTooth Connected. Starting Sheropanther...");

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            ActivityCompat.finishAffinity(this);

        } else {

            Log.e("Sphero", "Connection failed.");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //TODO
                    //connectionFailedDialog();
                }
            });

        }
        */
    }
}
