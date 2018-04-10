package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.View;

public class SensorView extends View {
    private Control controller;
    private Context context;
    private Point nextPosition;
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
            controller.drawPosition(canvas, nextPosition);
        }
    }


    public void setNewPosition(int x, int y) {
        nextPosition = new Point(x,y);
        postInvalidate();
    }

    public Control getController() {
        return controller;
    }
}
