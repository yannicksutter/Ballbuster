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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(w > 0 && h > 0){
            controller = new Control(getContext(), w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(controller != null){
            controller.drawPosition(canvas, nextPosition);
        }
    }
}
