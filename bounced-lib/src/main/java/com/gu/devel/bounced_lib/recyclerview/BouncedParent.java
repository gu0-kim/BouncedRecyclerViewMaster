package com.gu.devel.bounced_lib.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class BouncedParent extends FrameLayout {
  ObservableScroll mChild;
  Scroller mScroller;
  BounceAnimController mAnimController;
  private static final int MAX_SPEED = 200;

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

  public void startBounce() {
    Log.e("TAG", "---------------------startBounce--------------------");
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
    mScroller = new Scroller(getContext());
    mAnimController = new BounceAnimController();
  }

  public Scroller getScroller() {
    return mScroller;
  }

  public boolean isOverSpeed() {
    return true;
    // Math.abs(mScroller.getCurrVelocity()) < MAX_SPEED;
  }

  class BounceAnimController implements Runnable, ValueAnimator.AnimatorUpdateListener {

    ValueAnimator anim;

    BounceAnimController() {
      anim = new ValueAnimator();
      anim.addUpdateListener(this);
      anim.setInterpolator(new AccelerateDecelerateInterpolator());
      config(-200);
    }

    void config(int to) {
      anim.setDuration(800);
      anim.setIntValues(0, to, 0);
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
  }
}
