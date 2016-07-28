package com.frewen.freepickerview;

import android.os.Handler;
import android.os.Message;

/**
 * 消息Handler
 */
final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    /**
     * 滑动消息
     */
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    /**
     * 选中消息监听
     */
    public static final int WHAT_ITEM_SELECTED = 3000;

    final WheelView loopview;

    MessageHandler(WheelView loopview) {
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                loopview.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                loopview.smoothScroll(WheelView.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                loopview.onItemSelected();
                break;
        }
    }

}
