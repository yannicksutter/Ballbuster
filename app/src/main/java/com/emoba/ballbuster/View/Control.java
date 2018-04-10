package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;

import com.emoba.ballbuster.R;

public class Control {
    private int RADIUS_TOUCH = 20;
    private int RADIUS_GRID = 150;
    private int MARGIN_GRID = 80;

    private Context context;
    private Paint paint;
    private Point lastPosition;
    private int x = 0;
    private int y = 0;

    public Control(Context context){
        this.context = context;
        paint = new Paint();
        lastPosition = new Point(0,0);
    }

    public Control(Context context, int width, int height){
        this(context);
        int size;

        if(width > height){
            size = height;
        }else{
            size = width;
        }

        RADIUS_GRID = size / 3;
        MARGIN_GRID = (height - (2 * RADIUS_GRID)) / 3;
        RADIUS_TOUCH = size / 20;

        x = (int) width / 2;
        y = (int) RADIUS_GRID + MARGIN_GRID;
    }

    protected void drawPosition(Canvas canvas, Point nextPosition){
        drawMarker(canvas, x, y);

        //Draw circles
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        paint.setColor(color);

        if(nextPosition != null){
            if(nextPosition.x < RADIUS_TOUCH + 10){
                nextPosition.x = lastPosition.x;
            }else if (nextPosition.x > 2 * (RADIUS_GRID + MARGIN_GRID)){
                nextPosition.x = lastPosition.x;
            }

            lastPosition = nextPosition;

            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(lastPosition.x, lastPosition.y, RADIUS_TOUCH, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            canvas.drawCircle(lastPosition.x, lastPosition.y, RADIUS_TOUCH+10, paint);
        } else {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, RADIUS_TOUCH - 5, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawCircle(x, y, RADIUS_TOUCH, paint);
        }
    }

    private void drawMarker(Canvas canvas, int x, int y){
        //Draw Marker
        int color = ContextCompat.getColor(context, R.color.colorLine);
        paint.setColor(color);
        paint.setStrokeWidth(4);

        int xStart = x - RADIUS_GRID;
        int xEnd = x + RADIUS_GRID;
        canvas.drawLine(xStart,y,xEnd,y, paint);

        xStart = x;
        int yStart = y -RADIUS_GRID;
        int yEnd = y + RADIUS_GRID;
        canvas.drawLine(x, yStart, x , yEnd, paint);

        color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        canvas.drawCircle(x, y, RADIUS_GRID, paint);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRADIUS_GRID() {
        return RADIUS_GRID;
    }

    public int getMARGIN_GRID() {
        return MARGIN_GRID;
    }
}
