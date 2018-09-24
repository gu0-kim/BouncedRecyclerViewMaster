package com.gu.devel.bounced.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.OverScroller;

public abstract class BouncedParent<T> extends FrameLayout {
  protected T mChild;
  protected OverScroller mScroller;
  protected BounceAnimController mAnimController;
  protected boolean startFling = false;
  private boolean isAnimRunning;

  private static final int DO_PULL_ANIM_SPEED = 200;
  private static final int ANIM_DURATION = 300;

  public BouncedParent(@NonNull Context context) {
    this(context, null);
  }

  public BouncedParent(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (bindBouncedChild()) {
      init();
    } else {
      postException("Not find RecyclerView,Please check your code !");
    }
  }

  protected abstract boolean bindBouncedChild();

  protected void clear() {
    mAnimController.clear();
    mAnimController = null;
    sQuinticInterpolator = null;
  }

  protected boolean isOverSpeed() {
    return Math.abs(mScroller.getCurrVelocity()) > DO_PULL_ANIM_SPEED;
  }

  public void startBounce(boolean fromTop) {
    mAnimController.config(fromTop ? mScroller.getCurrVelocity() : -mScroller.getCurrVelocity());
    ViewCompat.postOnAnimation(this, mAnimController);
  }

  private void postException(String exception) {
    try {
      throw new Exception(exception);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void init() {
    mScroller = new OverScroller(getContext(), sQuinticInterpolator);
    mAnimController = new BounceAnimController();
  }

  @Override
  public void computeScroll() {
    super.computeScroll();
    if (mScroller != null && mScroller.computeScrollOffset()) {
      postInvalidate();
    }
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
    //    return isAnimRunning || super.dispatchTouchEvent(ev);
  }

  // copy from RecyclerView
  static Interpolator sQuinticInterpolator =
      new Interpolator() {
        @Override
        public float getInterpolation(float t) {
          t -= 1.0f;
          return t * t * t * t * t + 1.0f;
        }
      };

  class BounceAnimController
      implements Runnable, ValueAnimator.AnimatorUpdateListener, ValueAnimator.AnimatorListener {

    ValueAnimator anim;
    private int bouncedDis;
    boolean comeback;
    DecelerateInterpolator downInterpolator;
    LinearInterpolator upInterpolator;

    BounceAnimController() {
      downInterpolator = new DecelerateInterpolator();
      upInterpolator = new LinearInterpolator();
      anim = new ValueAnimator();
      anim.addUpdateListener(this);
      anim.addListener(this);
    }

    void config(float velocity) {
      comeback = false;
      anim.setDuration(ANIM_DURATION);
      bouncedDis = BouncedUtil.getDistanceByVelocity(velocity);
      anim.setInterpolator(downInterpolator);
      anim.setIntValues(0, -bouncedDis);
    }

    @Override
    public void run() {
      isAnimRunning = true;
      anim.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
      int scrollY = (Integer) animation.getAnimatedValue();
      scrollTo(0, scrollY);
    }

    @Override
    public void onAnimationStart(Animator animation) {}

    @Override
    public void onAnimationEnd(Animator animation) {
      if (!comeback) {
        anim.setInterpolator(upInterpolator);
        anim.setIntValues(-bouncedDis, 0);
        anim.start();
        comeback = true;
      } else {
        isAnimRunning = false;
      }
    }

    @Override
    public void onAnimationCancel(Animator animation) {}

    @Override
    public void onAnimationRepeat(Animator animation) {}

    void clear() {
      anim.removeAllUpdateListeners();
      anim.removeAllListeners();
      anim = null;
    }
  }
}
