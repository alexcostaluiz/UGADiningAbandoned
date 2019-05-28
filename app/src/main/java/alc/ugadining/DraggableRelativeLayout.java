package alc.ugadining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

public class DraggableRelativeLayout extends RelativeLayout {

    private boolean draggable;
    private View delete;
    private MealItemView parent;
    private long touchStart, touchEnd;
    private float transStart, transEnd;

    private float lastX;
    private boolean cancelClick;

    public DraggableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        draggable = false;
    }

    public void init() {
        parent = (MealItemView) getParent();
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public void setDelete(View delete) {
        this.delete = delete;
    }

    public boolean cancelClick() {
        if (cancelClick) {
            cancelClick = false;
            return true;
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!draggable) {
            return super.onTouchEvent(event);
        } else {
            //TODO: velocity + animations
            parent.onTouchEvent(event);
            super.onTouchEvent(event);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = event.getRawX();
                    touchStart = System.currentTimeMillis();
                    transStart = getTranslationX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getRawX();
                    float dx = x - lastX;
                    float transX = getTranslationX() + dx;
                    if (transX < 0) {
                        if (getTranslationX() < -delete.getWidth()) {
                            dx = (float) (dx * Math.pow(0.5,
                                    (1f/25f) * (-1f * (getTranslationX() + delete.getWidth()))));
                            transX = getTranslationX() + dx;
                        }
                        setTranslationX(transX);
                    } else if (transX > 0) {
                        if (getTranslationX() >= 0) {
                            dx = (float) (dx * Math.pow(0.5, (1f/25f) * getTranslationX()));
                            transX = getTranslationX() + dx;
                        }
                        setTranslationX(transX);
                    } else {
                        setTranslationX(0f);
                    }
                    lastX = x;
                    break;
                case MotionEvent.ACTION_UP:
                    touchEnd = System.currentTimeMillis();
                    transEnd = getTranslationX();
                    if (touchEnd - touchStart > 500 || Math.abs(transEnd - transStart) > 10) {
                        cancelClick = true;
                        if (getTranslationX() < delete.getWidth() / -2.5f) {
                            animate().translationX(-delete.getWidth())
                                    .setInterpolator(new OvershootInterpolator()).start();
                        } else {
                            animate().translationX(0f)
                                    .setInterpolator(new OvershootInterpolator()).start();
                        }
                    } else if (getTranslationX() < 0) {
                        animate().translationX(0f).setInterpolator(new OvershootInterpolator()).start();
                        if (getTranslationX() < -10) {
                            cancelClick = true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (getTranslationX() < delete.getWidth() / -2.5f) {
                        animate().translationX(-delete.getWidth())
                                .setInterpolator(new OvershootInterpolator()).start();
                    } else {
                        animate().translationX(0f)
                                .setInterpolator(new OvershootInterpolator()).start();
                    }
                    break;
            }
            return true;
        }
    }
}
