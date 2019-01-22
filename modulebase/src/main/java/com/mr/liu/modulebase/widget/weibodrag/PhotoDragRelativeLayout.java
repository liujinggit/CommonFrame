package com.mr.liu.modulebase.widget.weibodrag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:仿新浪微博图片拖拽效果
 */

public class PhotoDragRelativeLayout extends RelativeLayout {
    private PhotoDragDelegate mDragDelegate;

    public PhotoDragRelativeLayout(Context context) {
        this(context, null);
    }

    public PhotoDragRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoDragRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDragDelegate = new PhotoDragDelegate(context);
    }

    /**
     * @param listener
     */
    public void setDragListener(OnPhotoDragListener listener) {
        mDragDelegate.setDragListener(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = mDragDelegate.onInterceptTouchEvent(ev);
        if (intercept) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragDelegate.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public PhotoDragDelegate getDragDelegate() {
        return mDragDelegate;
    }
}
