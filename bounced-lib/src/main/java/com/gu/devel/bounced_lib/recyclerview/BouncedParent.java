package com.gu.devel.bounced_lib.recyclerview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.OverScroller;

public class BouncedParent extends FrameLayout {
  ObservableScroll mChild;
  OverScroller mScroller;
  BounceAnimController mAnimController;
  VelocityTracker mVelocityTracker;
  private static final int DO_PULL_ANIM_SPEED = 200;

  private static final String TAG = BouncedParent.class.getSimpleName();

  public BouncedParent(@NonNull Context context) {
    this(context, null);
  }

  public BouncedParent(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    View child = getChildAt(0);
    if (child instanceof ObservableScroll) {
      mChild = (ObservableScroll) child;
      mChild.setParent(this);
      return;
    }
    postException("Not find Bounced Child,Please check your code !");
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
    mVelocityTracker = VelocityTracker.obtain();
    mScroller = new OverScroller(getContext(), sQuinticInterpolator);
    mAnimController = new BounceAnimController();
  }

  public OverScroller getScroller() {
    return mScroller;
  }

  public boolean isOverSpeed() {
    return Math.abs(mScroller.getCurrVelocity()) > DO_PULL_ANIM_SPEED;
  }

  @Override
  public void computeScroll() {
    super.computeScroll();
    if (mScroller.computeScrollOffset()) {
      postInvalidate();
    }
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {

    return super.dispatchTouchEvent(ev);
  }

  // copy from RecyclerView
  static final Interpolator sQuinticInterpolator =
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

    public void config(float velocity) {
      comeback = false;
      anim.setDuration(300);
      bouncedDis = BouncedUtil.getDistanceByVelocity(velocity);
      anim.setInterpolator(downInterpolator);
      anim.setIntValues(0, -bouncedDis);
    }

    @Override
    public void run() {
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
      }
    }

    @Override
    public void onAnimationCancel(Animator animation) {}

    @Override
    public void onAnimationRepeat(Animator animation) {}
  }
}
