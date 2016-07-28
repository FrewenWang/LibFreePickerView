package com.frewen.freepickerview.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.frewen.freepickerview.WheelView;

public final class WheelViewGestureListener extends GestureDetector.SimpleOnGestureListener {

    private final WheelView mWheelView;

    public WheelViewGestureListener(WheelView wheelview) {
        this.mWheelView = wheelview;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mWheelView.scrollBy(velocityY);
        return true;
    }
}
