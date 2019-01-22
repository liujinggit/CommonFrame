package com.mr.liu.demo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.mr.liu.demo.R;

/**
 * Created by mrliu on 2018/10/9.
 * 此类用于: 自定义通用条目布局
 */
public class SettingItemView extends RelativeLayout {

    private TextView mTvForBackLeft, mTvTitle;
    private ImageView mIvNext;
    private Switch mSwitch;
    private ImageView mIvLeftIcon;

    public SettingItemView(Context context) {
        super(context);
        init(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        getValues(context, attrs);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        getValues(context, attrs);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.view_set_item, this);
        mIvLeftIcon = view.findViewById(R.id.iv_left);//最左边icon
        mTvTitle = (TextView) view.findViewById(R.id.tv_text);//最左边icon右边文字
        mTvForBackLeft = view.findViewById(R.id.tv_for_next_left);//下一步按钮左边文字
        mIvNext = (ImageView) view.findViewById(R.id.iv_next);//下一步按钮
        mSwitch = (Switch) view.findViewById(R.id.setting_item_switch);//切换按钮

    }

    public void getValues(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);

        //最左边按钮
        BitmapDrawable leftDrawableId = (BitmapDrawable) array.getDrawable(R.styleable.SettingItemView_icon_left);
        if (leftDrawableId != null) {
            mIvLeftIcon.setImageDrawable(leftDrawableId);
        } else {
            mIvLeftIcon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
        }

        //最左边icon右边文字
        String text = array.getString(R.styleable.SettingItemView_text_title);
        mTvTitle.setText(text);

        //下一步按钮
        BitmapDrawable drawableId = (BitmapDrawable) array.getDrawable(R.styleable.SettingItemView_next_iv);
        if (drawableId != null) {
            mIvNext.setImageDrawable(drawableId);
        } else {
            mIvNext.setImageResource(R.mipmap.icon_right);
        }

        //切换按钮
        boolean isVisibleSwitch = array.getBoolean(R.styleable.SettingItemView_show_switch, false);
        setSBVisible(isVisibleSwitch);
        array.recycle();
    }

    /**
     * 是否显示切换按钮
     */
    private void setSBVisible(boolean isVisibleSwitch) {
        if (isVisibleSwitch) {
            mIvNext.setVisibility(GONE);
            mSwitch.setVisibility(VISIBLE);
        } else {
            mSwitch.setVisibility(GONE);
            mIvNext.setVisibility(VISIBLE);
        }

    }

    /**
     * 设置next键左边文字
     */
    public void setText(String text) {
        if (mTvForBackLeft != null) {
            mTvForBackLeft.setText(text);
        }
    }


    /**
     * 设置开关的状态
     */
    public void setSBStatus(boolean flag) {
        mSwitch.setChecked(flag);
    }

    /**
     * 设置按钮点击监听
     */
    public void setSBListener(CompoundButton.OnCheckedChangeListener listener) {
        mSwitch.setOnCheckedChangeListener(listener);
    }

}
