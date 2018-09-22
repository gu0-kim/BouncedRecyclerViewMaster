package com.gu.devel.bounced_lib.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class BouncedParent extends FrameLayout {
  ObservableScroll mChild;
  Scroller mScroller;

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
      return;
    }
    postException("Not find Bounced Child,Please check your code !");
  }

  private void postException(String exception) {
    try {
      throw new Exception(exception);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void init() {}

  public Scroller getScroller() {
    return mScroller;
  }
}
