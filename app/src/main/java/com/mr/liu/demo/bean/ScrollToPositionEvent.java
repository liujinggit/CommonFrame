package com.mr.liu.demo.bean;

/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:
 */

public class ScrollToPositionEvent {

    public int position = 0;
    public int delayDuration = 0;
    public boolean delayEnable;
    public OnRegionListener listener;

    public ScrollToPositionEvent(int position, int delayDuration, boolean delayEnable, OnRegionListener listener) {
        this.position = position;
        this.delayDuration = delayDuration;
        this.delayEnable = delayEnable;
        this.listener = listener;
    }

    public interface OnRegionListener {
        void onRegion(int l, int t, int r, int b, int w, int h);
    }
}
