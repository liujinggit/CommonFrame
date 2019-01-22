package com.mr.liu.modulebase.widget.circle;

/**
 * Created by mrliu on 2019/1/18.
 * 此类用于:拖拽监听
 */

public interface DragListener {

    // 开始拖拽 执行一次
    void onStartDrag();

    // 开始动画进入

    /**
     * @param outOfBound 点击的视图是否超出屏幕
     */
    void onStartEnter(boolean outOfBound);

    // 进入的动画结束
    void onEndEnter();

    // 退出的动画开始
    void onStartExit(boolean outOfBound);

    void onEndExit();

    // 释放

    /**
     * @param isResume 是否恢复
     */
    void onRelease(boolean isResume);

    /**
     * 恢复动画结束
     */
    void onEndResume();
}
