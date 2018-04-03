package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;

import com.emoba.ballbuster.R;

public class Control {
    private static int RADIUS_TOUCH = 20;
    private static int RADIUS_GRID = 150;
    private static int MARGIN_GRID = 80;

    private Context context;
    private Paint paint;
    private Point lastPosition;

    public Control(Context context){
        this.context = context;
        paint = new Paint();
        lastPosition = new Point(0,0);
    }

    protected void drawPosition(Canvas canvas, Point nextPosition){
        int x = (int) canvas.getWidth() / 2;
        int y = (int) canvas.getHeight() / 2;

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
            paint.setStyle(Paint.Style.STROKE);
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
        int yStart = y;
        int yEnd = y;
        canvas.drawLine(xStart,yStart,xEnd,yEnd, paint);

        xStart = x;
        yStart = y -RADIUS_GRID;
        xEnd = x;
        yEnd = y + RADIUS_GRID;
        canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);

        color = ContextCompat.getColor(context, R.color.colorCirle);
        paint.setColor(color);

    }

}
