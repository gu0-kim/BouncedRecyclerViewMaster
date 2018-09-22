package com.gu.devel.bounced_lib.recyclerview;

import android.widget.Scroller;

public interface ObservableScroll {
  boolean isScroll2Top();

  boolean isScroll2Bottom();

  Scroller getScroller();
}
