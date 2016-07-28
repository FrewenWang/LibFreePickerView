package com.frewen.freepickerview;

import java.util.TimerTask;

public final class SmoothScrollTimerTask extends TimerTask{

    int realTotalOffset;
    int realOffset;
    int offset;
    final WheelView mWheelView;



    public SmoothScrollTimerTask(WheelView wheelView, int offset) {
        this.mWheelView = wheelView;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分成十小份，按是小份单位来重绘
        realOffset = (int) ((float) realTotalOffset * 0.1F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            mWheelView.cancelFuture();
            mWheelView.mHandler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
        } else {
            mWheelView.totalScrollY = mWheelView.totalScrollY + realOffset;

            //这里如果不是循环模式，则点击空白位置需要回滚，不然就会出现选到－1 item的 情况
            if (!mWheelView.isLoop) {
                float itemHeight = mWheelView.itemHeight;
                float top = (float) (-mWheelView.initPosition) * itemHeight;
                float bottom = (float) (mWheelView.getItemsCount() - 1 - mWheelView.initPosition) * itemHeight;
                if (mWheelView.totalScrollY <= top||mWheelView.totalScrollY >= bottom) {
                    mWheelView.totalScrollY = mWheelView.totalScrollY - realOffset;
                    mWheelView.cancelFuture();
                    mWheelView.mHandler.sendEmptyMessage(MessageHandler.WHAT_ITEM_SELECTED);
                    return;
                }
            }
            mWheelView.mHandler.sendEmptyMessage(MessageHandler.WHAT_INVALIDATE_LOOP_VIEW);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
