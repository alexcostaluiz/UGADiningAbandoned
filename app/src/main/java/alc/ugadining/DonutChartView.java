package alc.ugadining;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

public class DonutChartView extends View {

    public static float EPSILON = 1e-5f;

    RectF oval;
    Paint arcPaint, arcBackgroundPaint;
    float strokeWidth;
    List<Float> values;
    float fraction;
    int[] colors;

    public DonutChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setRotation(-90f);
        strokeWidth = 20f;
        values = new ArrayList<>();
        fraction = 1f;
        init();
    }

    private void init() {
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setColor(Color.RED);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(strokeWidth);

        arcBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcBackgroundPaint.setColor(0xFFF0F0F0);
        arcBackgroundPaint.setStyle(Paint.Style.STROKE);
        arcBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        arcBackgroundPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        oval = new RectF(strokeWidth / 2f, strokeWidth / 2f,
                w - strokeWidth / 2f, h - strokeWidth / 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(oval, 0f, 360f, false, arcBackgroundPaint);
        for (int i = values.size() - 1; i >= 0; i--) {
            arcPaint.setColor(colors[i]);
            canvas.drawArc(oval, 0f, values.get(i), false, arcPaint);
        }
    }

    public void setValues(boolean animate, float... values) {
        float sum = 0;
        for (float f : values) {
            sum += f;
        }
        if (Math.abs(1 - sum) > EPSILON) {
            throw new IllegalArgumentException("Values must add up to 1.");
        }

        if (this.values.isEmpty()) {
            for (int i = 0; i < values.length; i++) {
                this.values.add(0f);
            }
        }

        float[] newValues = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            float newF = values[i] * 360;
            if (i > 0) {
                newF += newValues[i - 1];
            }
            newValues[i] = newF;
        }

        if (animate) {
            animateValues(newValues);
        } else {
            this.values.clear();
            for (float f : newValues) {
                this.values.add(f);
            }
            invalidate();
        }
    }

    private void animateValues(float[] newValues) {
        float[] initialValues = new float[values.size()];
        for (int i = 0; i < initialValues.length; i++) {
            initialValues[i] = this.values.get(i);
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {
            fraction = animation.getAnimatedFraction();
            for (int i = 0; i < newValues.length; i++) {
                float val = initialValues[i] + ((newValues[i] - initialValues[i]) * fraction);
                values.set(i, val);
            }
            invalidate();
        });
        animator.setDuration(777L);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }
}
