package alc.ugadining;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import java.util.Timer;
import java.util.TimerTask;

public class BounceScrollView extends ScrollView {

    int titleId;
    int toolbarTitleId;
    View innerLayout;
    TextView title;
    TextView toolbarTitle;
    ObjectAnimator fadeIn;
    ObjectAnimator fadeOut;

    float lastY, initialY;
    boolean isScrolling;
    ViewConfiguration vc;
    int ySlop;

    private OverScroller scroller;
    private VelocityTracker velocityTracker = null;
    private int maxVelocity, minVelocity;
    float overVelocity;
    boolean positiveVelocity;

    ObjectAnimator springDownAnim, springUpAnim;
    ValueAnimator textAnimator;
    Timer scrollAnim;

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BounceScrollView,
                0, 0
        );

        try {
            titleId = a.getResourceId(R.styleable.BounceScrollView_title_id, 0);
            toolbarTitleId = a.getResourceId(R.styleable.BounceScrollView_toolbar_title_id, 0);
        } finally {
            a.recycle();
        }

        setVerticalScrollBarEnabled(false);
        scroller = new OverScroller(context, null);
        scroller.setFriction(.01f);
        vc = ViewConfiguration.get(context);
        ySlop = vc.getScaledTouchSlop();
        maxVelocity = vc.getScaledMaximumFlingVelocity();
        minVelocity = vc.getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (titleId != 0 && toolbarTitleId != 0) {
            innerLayout = getChildAt(0);
            title = findViewById(titleId);
            title.setPivotX(0f);
            toolbarTitle = ((View) getParent().getParent()).findViewById(toolbarTitleId);
            fadeIn = ObjectAnimator.ofFloat(toolbarTitle, "alpha", 1f).setDuration(150L);
            fadeOut = ObjectAnimator.ofFloat(toolbarTitle, "alpha", 0f).setDuration(150L);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            springBack();
            isScrolling = false;
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                initialY = event.getRawY();
                cancelAnimations();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isScrolling) {
                    return true;
                }
                final float y = event.getRawY();
                final float dy = Math.abs(y - initialY);

                if (dy > ySlop) {
                    isScrolling = true;
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelAnimations();
                scroller.forceFinished(true);
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                velocityTracker.addMovement(event);
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                    lastY = event.getRawY();
                }
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                float y = event.getRawY();
                float dy = y - lastY;
                if (innerLayout.getY() >= 0f && dy > 0f) {
                    dy = (float) (dy * Math.pow(0.5, innerLayout.getY() / 75f));
                    title.setScaleX(1 + innerLayout.getY() / 1500f);
                    title.setScaleY(1 + innerLayout.getY() / 1500f);
                } else if (innerLayout.getY() <= (getHeight() - innerLayout.getHeight()) && dy < 0f) {
                    dy = (float) (dy * Math.pow(0.5, Math.abs(innerLayout.getY() + (innerLayout.getHeight() - getHeight())) / 75f));
                }
                innerLayout.setY(innerLayout.getY() + dy);
                lastY = y;

                animateToolbarTitle();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isScrolling = false;

                if (velocityTracker != null) {
                    float velocityY = velocityTracker.getYVelocity();
                    positiveVelocity = velocityY >= 0;
                    if (Math.abs(velocityY) > minVelocity && !isOverscrolled()) {
                        scroller.fling(0, (int) innerLayout.getY(), 0, (int) velocityY,
                                0, 0, getHeight() - innerLayout.getHeight(), 0, 0, 200);
                        ViewCompat.postInvalidateOnAnimation(this);
                    } else {
                        springBack();
                    }
                    velocityTracker.recycle();
                    velocityTracker = null;
                } else {
                    springBack();
                }
                break;
        }
        return true;
    }

    private boolean isOverscrolled() {
        return innerLayout.getY() > 0 || innerLayout.getY() < getHeight() - innerLayout.getHeight();
    }

    private void springBack() {
        if (innerLayout.getY() > 0f) {
            springDownAnim = ObjectAnimator.ofFloat(innerLayout, "y", 0f);
            springDownAnim.setDuration(333L);
            springDownAnim.setInterpolator(new DecelerateInterpolator());
            springDownAnim.start();

            textAnimator = ValueAnimator.ofFloat(title.getScaleX(), 1);
            textAnimator.addUpdateListener(animation -> {
                title.setScaleX((float) animation.getAnimatedValue());
                title.setScaleY((float) animation.getAnimatedValue());
            });
            textAnimator.setDuration(333L).setInterpolator(new DecelerateInterpolator());
            textAnimator.start();
        } else if (innerLayout.getY() < (getHeight() - innerLayout.getHeight())) {
            springUpAnim = ObjectAnimator.ofFloat(innerLayout, "y", getHeight() - innerLayout.getHeight());
            springUpAnim.setDuration(333L);
            springUpAnim.setInterpolator(new DecelerateInterpolator());
            springUpAnim.start();
        }
    }

    private void cancelAnimations() {
        if (springDownAnim != null && springDownAnim.isStarted()) {
            springDownAnim.cancel();
        }
        if (springUpAnim != null && springUpAnim.isStarted()) {
            springUpAnim.cancel();
        }
        if (textAnimator != null && textAnimator.isStarted()) {
            textAnimator.cancel();
        }
        if (scrollAnim != null) {
            scrollAnim.cancel();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            animateToolbarTitle();
            if (!scroller.isOverScrolled()) {
                innerLayout.setY(scroller.getCurrY());
                animateToolbarTitle();
            } else {
                System.out.println("animating");
                overVelocity = scroller.getCurrVelocity();
                overVelocity = positiveVelocity ? overVelocity : -overVelocity;
                scroller.forceFinished(true);
                scrollAnim = new Timer();
                scrollAnim.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        overVelocity *= .975f;
                        innerLayout.setY(innerLayout.getY() + overVelocity / 1000);
                        if (innerLayout.getY() > 0) {
                            title.setScaleX(1 + innerLayout.getY() / 1500f);
                            title.setScaleY(1 + innerLayout.getY() / 1500f);
                        }
                        if (Math.abs(overVelocity) < minVelocity) {
                            scrollAnim.cancel();
                            post(() -> springBack());
                            post(() -> animateToolbarTitle());
                        }
                    }
                }, 0, 1);
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void animateToolbarTitle() {
        if (-innerLayout.getY() >= title.getHeight()) {
            if (fadeOut.isStarted()) {
                fadeOut.cancel();
            }
            if (toolbarTitle.getAlpha() != 1f && !fadeIn.isStarted()) {
                fadeIn.start();
            }
        } else {
            if (fadeIn.isStarted()) {
                fadeIn.cancel();
            }
            if (toolbarTitle.getAlpha() != 0f && !fadeOut.isStarted()) {
                fadeOut.start();
            }
        }
    }

    public void updateToolbarTitle() {
        if (-innerLayout.getY() >= title.getHeight() && title.getHeight() != 0f) {
            toolbarTitle.setAlpha(1f);
        } else {
            toolbarTitle.setAlpha(0f);
        }
    }
}
