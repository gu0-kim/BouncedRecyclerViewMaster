package com.gu.devel.bounced_lib.recyclerview;

public interface ObservableScroll {
  boolean isScroll2Top();

  boolean isScroll2Bottom();

  void setParent(BouncedParent parent);
}
