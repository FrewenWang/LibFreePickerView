package com.frewen.freepickerview;

import java.util.TimerTask;

public final class InertialTimerTask extends TimerTask {

    private WheelView mWheelView;
    float a;
    final float velocityY;

    public InertialTimerTask(WheelView loopview, float velocityY) {
        super();
        mWheelView = loopview;
        this.velocityY = velocityY;
        a = Integer.MAX_VALUE;
    }

    @Override
    public void run() {
        {
            if (a == Integer.MAX_VALUE) {
                if (Math.abs(velocityY) > 2000F) {
                    if (velocityY > 0.0F) {
                        a = 2000F;
                    } else {
                        a = -2000F;
                    }
                } else {
                    a = velocityY;
                }
            }
            if (Math.abs(a) >= 0.0F && Math.abs(a) <= 20F) {
                mWheelView.cancelFuture();
                mWheelView.mHandler.sendEmptyMessage(MessageHandler.WHAT_SMOOTH_SCROLL);
                return;
            }
            int i = (int) ((a * 10F) / 1000F);
            mWheelView.totalScrollY = mWheelView.totalScrollY - i;
            if (!mWheelView.isLoop) {
                float itemHeight = mWheelView.itemHeight;
                float top = (-mWheelView.initPosition) * itemHeight;
                float bottom = (mWheelView.getItemsCount() - 1 - mWheelView.initPosition) * itemHeight;
                if (mWheelView.totalScrollY - itemHeight * 0.3 < top) {
                    top = mWheelView.totalScrollY + i;
                } else if (mWheelView.totalScrollY + itemHeight * 0.3 > bottom) {
                    bottom = mWheelView.totalScrollY + i;
                }

                if (mWheelView.totalScrollY <= top) {
                    a = 40F;
                    mWheelView.totalScrollY = (int) top;
                } else if (mWheelView.totalScrollY >= bottom) {
                    mWheelView.totalScrollY = (int) bottom;
                    a = -40F;
                }
            }
            if (a < 0.0F) {
                a = a + 20F;
            } else {
                a = a - 20F;
            }
            mWheelView.mHandler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
        }
    }
}
