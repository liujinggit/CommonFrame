package com.mr.liu.demo.widget.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.mr.liu.modulebase.R;
import com.mr.liu.modulebase.base.LibApplication;

/**
 * Created by mrliu on 2018/12/31.
 * 此类用于:设置spannable
 */

public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener  {
    private int DEFAULT_COLOR_ID = R.color.color_8290AF;
    /**
     * text颜色
     */
    private int textColor ;

    public SpannableClickable() {
        this.textColor = LibApplication.getInstance().getResources().getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor){
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
