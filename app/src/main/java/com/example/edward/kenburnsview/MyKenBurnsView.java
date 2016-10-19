package com.example.edward.kenburnsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by edward on 16/6/23.
 */
public class MyKenBurnsView extends ImageView {


    private Scroller mScroller;

    boolean burnable = false;
    int destX;//the extra margin of image at one side
    int duration;
    int rollingDirection = RollingDirection.LEFT;

    public MyKenBurnsView(Context context) {
        super(context);

        init(context, null);
    }

    public MyKenBurnsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context, new LinearInterpolator());
        setScaleType(ScaleType.CENTER_CROP);
    }

    public void burn(int duration) {

        this.duration = duration;
        resetToLeft();

        Drawable drawable = getDrawable();

        if (drawable != null) {
            try {

                burnable = isBurnable();

                if (burnable) {

                    int bitmapWidth = drawable.getIntrinsicWidth(); //this is the bitmap's width
                    int bitmapHeight = drawable.getIntrinsicHeight(); //this is the bitmap's height

                    float ratio = (float) bitmapHeight / (float) getHeight();

                    destX = (int) (bitmapWidth / ratio - getWidth()) / 2;

                    if (rollingDirection == RollingDirection.LEFT) {
                        scrollTo(-destX, 0);
                    } else if (rollingDirection == RollingDirection.RIGHT) {
                        scrollTo(destX, 0);
                    }
                } else {
                    System.out.println("not burnable");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void smoothScrollTo(int destX, int duration) {
        int scrollX = this.getScrollX();
        int deltaX = destX - scrollX;
        mScroller.startScroll(scrollX, 0, deltaX, 0, duration);
        this.invalidate();
    }

    @Override
    public void computeScroll() {
        if (burnable) {
            if (mScroller.computeScrollOffset()) {
                this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                postInvalidate();
            } else {
                //scroll completed
                System.out.println("scroll complete");
                smoothScrollTo(destX, duration);
            }
        }
    }

    public void resetToLeft() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        scrollTo(-destX, 0);
    }

    public void setDrawable(int res) {
        setImageResource(res);
        scrollTo(-destX, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        computeScroll();
    }

    public boolean isBurnable() {

        boolean burnable;

        Drawable drawable = getDrawable();
        float viewRatio = getWidth() * 1f / getHeight() * 1f;
        if (drawable != null) {
            int bitmapWidth = drawable.getIntrinsicWidth(); //this is the bitmap's width
            int bitmapHeight = drawable.getIntrinsicHeight(); //this is the bitmap's height

            float drawableRatio = bitmapWidth * 1f / bitmapHeight * 1f;
            burnable = (drawableRatio >= viewRatio);

            return burnable;
        } else {
            return false;
        }

    }

    public static class RollingDirection {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
    }
}
