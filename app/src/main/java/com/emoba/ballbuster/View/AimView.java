package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AimView extends View {

    private Control controller;

    private static final int RADIUS = 250;
    Point newPosition = null;
    Paint paint;
    int x;
    int y;

    Point center;
    public AimView(Context context) {
        super(context);
        paint = new Paint();
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

    private double angle(double cx, double cy, double x, double y) {
        double deltaX = x - cx;
        double deltaY = y - cy;
        double radius = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
        double nx = deltaX / radius;
        double ny = deltaY / radius;
        double theta = Math.toRadians(90) + Math.atan2(ny, nx);

        return Double.compare(theta, 0.0) >= 0 ? Math.toDegrees(theta) : Math.toDegrees((theta)) + 360.0;
    }

    private Point pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point((int) (cX + (radius * Math.sin(Math.toRadians(angle)))),
                         (int) (cY - (radius * Math.cos(Math.toRadians(angle)))));
    }


    public float getAngleOfPointOnCircle() {
        return (float) (180-angle(controller.getX(), controller.getY(), x, y));
    }

    public void setNewPosition(int x, int y) {

        this.x = x;
        this.y = y;
        double angle = angle(controller.getX(), controller.getY(), x, y);
        newPosition = pointOnCircle(controller.getX(), controller.getY(), controller.getRADIUS_GRID(), angle);

        postInvalidate();
    }

    public Control getController() {
        return controller;
    }
}
