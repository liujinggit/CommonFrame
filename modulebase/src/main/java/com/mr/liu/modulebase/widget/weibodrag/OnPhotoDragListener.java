package com.mr.liu.modulebase.widget.weibodrag;

import android.view.View;

/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:
 */

public interface OnPhotoDragListener {

    /**
     * @return 动画是否在运行
     */
    boolean isAnimationRunning();

    /**
     * 拖动的Y坐标
     *
     * @param dy
     */
    void onDrag(float dy);

    /**
     * 释放
     */
    void onRelease();

    /**
     * 拽动view
     *
     * @return
     */
    View getDragView();
}
