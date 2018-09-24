package com.gu.devel.bounced_lib.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class BouncedRecyclerView extends RecyclerView implements ObservableScroll {
  private static final String TAG = BouncedRecyclerView.class.getSimpleName();
  private static final int DIRECTION_UP = -1;
  private static final int DIRECTION_DOWN = 1;
  private BouncedParent mParent;
  private boolean need = false;

  public BouncedRecyclerView(Context context) {
    this(context, null);
  }

  public BouncedRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    addOnScrollListener(new ScrollListener());
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
  }

  @Override
  public void setParent(BouncedParent parent) {
    this.mParent = parent;
  }

  @Override
  public boolean isScroll2Top() {
    return !canScrollVertically(DIRECTION_UP);
  }

  @Override
  public boolean isScroll2Bottom() {
    return !canScrollVertically(DIRECTION_DOWN);
  }

  @Override
  public boolean fling(int velocityX, int velocityY) {
    Log.e(TAG, "----fling: (velocityX=" + velocityX + ",velocityY=" + velocityY + ")");
    mParent.getScroller().forceFinished(true);
    mParent
        .getScroller()
        .fling(
            0,
            0,
            0,
            velocityY,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE,
            Integer.MIN_VALUE,
            Integer.MAX_VALUE);
    mParent.postInvalidate();
    return super.fling(velocityX, velocityY);
  }

  class ScrollListener extends OnScrollListener {
    private int scrollY;

    ScrollListener() {
      super();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      scrollY += dy;
      if (dy < 0 && isScroll2Top()) {
        if (need && mParent.isOverSpeed()) {
          mParent.startBounce(true);
        }
      } else if (dy > 0 && isScroll2Bottom()) {
        if (need && mParent.isOverSpeed()) {
          mParent.startBounce(false);
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
        need = false;
        printLog("静止状态");
      } else if (newState == SCROLL_STATE_DRAGGING) {
        printLog("拖动ing");
      } else if (newState == SCROLL_STATE_SETTLING) {
        need = true;
        printLog("自己滚动ing");
      }
    }
  }

  private void printLog(String log) {
    Log.e(TAG, log);
  }
}
