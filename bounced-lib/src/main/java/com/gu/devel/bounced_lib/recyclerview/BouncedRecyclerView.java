package com.gu.devel.bounced_lib.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

public class BouncedRecyclerView extends RecyclerView implements ObservableScroll {
  private static final String TAG = BouncedRecyclerView.class.getSimpleName();
  private static final int DIRECTION_UP = -1;
  private static final int DIRECTION_DOWN = 1;
  private Scroller mFlingScroller;
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
    mFlingScroller = getScroller();
  }

  @Override
  public Scroller getScroller() {
    View parent = ((View) getParent());
    Scroller scroller = null;
    try {
      BouncedParent bp = (BouncedParent) parent;
      scroller = bp.getScroller();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return scroller;
  }

  @Override
  public boolean isScroll2Top() {
    return !canScrollVertically(DIRECTION_UP);
  }

  @Override
  public boolean isScroll2Bottom() {
    return !canScrollVertically(DIRECTION_DOWN);
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
      if (scrollY == 0) printLog("---到顶了---");
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      printState(newState);
    }

    private void printState(int newState) {
      if (newState == SCROLL_STATE_IDLE) {
        printLog("静止状态");
      } else if (newState == SCROLL_STATE_DRAGGING) {
        printLog("拖动ing");
      } else if (newState == SCROLL_STATE_SETTLING) {
        printLog("自己滚动ing");
      }
    }
  }

  private void printLog(String log) {
    Log.e(TAG, log);
  }
}
