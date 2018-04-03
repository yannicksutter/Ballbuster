package com.emoba.ballbuster.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AimView extends View {

    Point newPosition = null;
    Paint paint;
    public AimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth()/2, getHeight()/2, 5, paint);

        canvas.drawCircle(newPosition.x, newPosition.y, 10, paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                newPosition = new Point(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                newPosition.set(x, y);
                break;
            default:
                newPosition = null;
        }

        postInvalidate();

        return true;
    }
}
