package com.gu.devel.bounced.base;

public class BouncedUtil {
  private static final int MIN_SPEED = 500;
  private static final int MAX_SPEED = 6000;
  private static final int MAX_OVER_DISTANCE = 200;
  private static final int MIN_OVER_DISTANCE = 50;
  private static final float rate =
      (MAX_OVER_DISTANCE - MIN_OVER_DISTANCE) * 1.0f / (MAX_SPEED - MIN_SPEED);

  // 通过速度计算惯性距离
  public static int getDistanceByVelocity(float velocity) {
    float signum = Math.signum(velocity);
    return (int)
        (signum * ((getAbsValidVelocity(velocity) - MIN_SPEED) * rate + MIN_OVER_DISTANCE));
  }

  private static float getAbsValidVelocity(float velocity) {
    velocity = Math.abs(velocity);
    return Math.min(Math.max(MIN_SPEED, velocity), MAX_SPEED);
  }
}
