package alc.ugadining;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class LoadingBar extends View {

    ValueAnimator animator;
    int progress;
    Paint rectPaint;
    Rect rect;
    int width, height;

    public LoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        animator = new ValueAnimator();
        progress = 0;
        init();
    }

    public void init() {
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(0xFFD32F2F);
        rectPaint.setStyle(Paint.Style.FILL);

        rect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = (int) (progress / 100f * width);

        rect.set(0, 0, w, height);
        canvas.drawRect(rect, rectPaint);
    }

    private void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setProgress(int progress, boolean animate) {
        cancelAnimator();
        if (animate) {
            animator.removeAllListeners();
            animator.setIntValues(this.progress, progress);
            animator.addUpdateListener(animation ->
                    setProgress((int) animation.getAnimatedValue()));
            animator.setDuration(getDuration(Math.abs(progress - this.progress)));
            animator.setInterpolator(new DecelerateInterpolator());
            if (progress == 100) {
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setProgress(0);
                        hide();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        setProgress(0);
                        hide();
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            animator.start();
        } else {
            setProgress(progress);
        }
    }

    private long getDuration(int percent) {
        double a = 13.0/15000.0;
        double b = 17.0/300.0;
        double c = percent * -1.0;
        return (long) ((-b + Math.sqrt(Math.pow(b , 2) - 4.0 * a * c)) / (2.0 * a));
    }

    private void cancelAnimator() {
        if (animator.isStarted()) {
            animator.cancel();
        }
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }
}
