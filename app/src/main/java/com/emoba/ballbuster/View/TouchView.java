package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;

public class TouchView extends View {

    private Control controller;
    private Point nextPosition;

    private Handler robotControlHandler;

    public TouchView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(controller != null){
            controller.drawPosition(canvas, nextPosition);
        }
    }
}
