package com.gu.devel.bounced.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.gu.devel.bounced.base.BouncedParent;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class BouncedRecyclerViewParent extends BouncedParent<RecyclerView> {
  private static final int DIRECTION_UP = -1;
  private static final int DIRECTION_DOWN = 1;

  public BouncedRecyclerViewParent(@NonNull Context context) {
    super(context);
  }

  public BouncedRecyclerViewParent(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected boolean bindBouncedChild() {
    View child = getChildAt(0);
    if (child != null && child instanceof RecyclerView) {
      mChild = (RecyclerView) child;
      mChild.addOnScrollListener(new ScrollListener());
      mChild.setOnFlingListener(new RecyclerViewFlingListener());
      return true;
    }
    return false;
  }

  @Override
  public void clear() {
    super.clear();
    if (mChild != null) {
      mChild.setOnFlingListener(null);
      mChild.clearOnScrollListeners();
      mChild = null;
    }
  }

  class ScrollListener extends RecyclerView.OnScrollListener {

    ScrollListener() {
      super();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      if (dy < 0 && !recyclerView.canScrollVertically(DIRECTION_UP)) {
        if (startFling && isOverSpeed()) {
          startBounce(true);
        }
      } else if (dy > 0 && !recyclerView.canScrollVertically(DIRECTION_DOWN)) {
        if (startFling && isOverSpeed()) {
          startBounce(false);
        }
      }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      updateState(newState);
    }

    private void updateState(int newState) {
      if (newState == SCROLL_STATE_IDLE) {
        startFling = false;
      } else if (newState == SCROLL_STATE_SETTLING) {
        startFling = true;
      }
    }
  }

  // 获取fling时候的速度，启动scroller fling
  class RecyclerViewFlingListener extends RecyclerView.OnFlingListener {
    @Override
    public boolean onFling(int velocityX, int velocityY) {
      mScroller.forceFinished(true);
      mScroller.fling(
          0,
          0,
          0,
          velocityY,
          Integer.MIN_VALUE,
          Integer.MAX_VALUE,
          Integer.MIN_VALUE,
          Integer.MAX_VALUE);
      postInvalidate();
      mChild.setOnFlingListener(null);
      boolean result = mChild.fling(velocityX, velocityY);
      mChild.setOnFlingListener(this);
      return result;
    }
  }
}
