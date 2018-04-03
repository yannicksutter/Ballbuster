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


    private static final int RADIUS = 250;
    Point newPosition = null;
    Paint paint;

    Point center;
    public AimView(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        center = new Point(getWidth()/2, getHeight()/2);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);

        canvas.drawCircle(center.x, center.y, RADIUS, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        if(newPosition != null) {
            canvas.drawCircle(newPosition.x, newPosition.y, 25, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int x = (int) event.getX();
        int y = (int) event.getY();
        double angle = angle(center.x, center.y, x, y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newPosition = pointOnCircle(center.x, center.y, RADIUS, angle);
                break;
            case MotionEvent.ACTION_MOVE:
                Point point = pointOnCircle(center.x, center.y, RADIUS, angle);
                newPosition.set(point.x, point.y);
                break;
            default:
        }

        postInvalidate();

        return true;
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

    public double getAngleForHeading(double x, double y) {
        float angle = (float) Math.toDegrees(Math.atan2(y - center.y+RADIUS, x-center.x));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

}
