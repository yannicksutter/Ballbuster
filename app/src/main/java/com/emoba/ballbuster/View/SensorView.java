package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.hardware.Sensor.TYPE_ROTATION_VECTOR;

public class SensorView extends View {
    private Control controller;
    private Context context;
    private Point newPosition;
    private Object canvas;

    public SensorView(Context context) {

        super(context);
        this.context = context;

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        if(w > 0 && h > 0){
            controller = new Control(getContext(), w, h);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(controller != null){
            controller.drawPosition(canvas, newPosition);
        }
    }


    public void setNewPosition(int x, int y) {

        newPosition = new Point(x,y);
        Log.d("lsdk",x + " " + y);
        postInvalidate();
    }
}
