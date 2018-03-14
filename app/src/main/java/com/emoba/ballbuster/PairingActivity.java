package com.emoba.ballbuster;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PairingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        /*BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(PairingActivity.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Bluetooth is not available");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            AlertDialog alertDialog = new AlertDialog.Builder(PairingActivity.this).create();
            alertDialog.setTitle("Info");
            alertDialog.setMessage("Bluetooth is not active");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }*/

        //if connection successful
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
