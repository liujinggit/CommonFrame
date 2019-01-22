package com.mr.liu.modulebase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.mr.liu.modulebase.R;
import com.mr.liu.modulebase.constent.LibConstant;
import com.mr.liu.modulebase.utils.CustomToast;
import com.mr.liu.modulebase.view.dialog.loading.LoadingUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:总模块基类的Activity
 */

public abstract class LibBaseActivity extends SwipeBackActivity implements LibInterface {

    protected Context mContext;
    private ViewStub emptyView;
    protected Dialog mProgressDialog;
    protected TextView mTvTitleLeft;
    protected ImageButton mBtTitleLeft;
    protected TextView mTvTitleRight;
    protected TextView mTvTitleName;
    protected ImageButton mBtTitleRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.base_activity_base);
        ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(savedInstanceState), null));

        LibApplication.getInstance().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void showDataException(String msg) {
        toastShort(msg);
    }

    @Override
    public void showNetworkException() {
        toastShort(R.string.msg_network_error);
    }

    @Override
    public void showUnknownException() {
        toastShort(R.string.msg_unknown_error);
    }

    //填充布局
    protected abstract int getLayoutId(Bundle savedInstanceState);


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();


    /**
     * 左边的布局ImageButton
     *
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalLeft(T t) {
        mBtTitleLeft = (ImageButton) findViewById(R.id.bt_global_title_left);
        if (t instanceof Integer) {
            mBtTitleLeft.setImageResource((Integer) t);
        } else if (t instanceof Drawable) {
            mBtTitleLeft.setImageDrawable((Drawable) t);
        }
        mBtTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局ImageButton
     *
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalRight(T t) {
        mBtTitleRight = (ImageButton) findViewById(R.id.bt_global_title_right);
        if (t instanceof Integer) {
            mBtTitleRight.setImageResource((Integer) t);
        } else if (t instanceof Drawable) {
            mBtTitleRight.setImageDrawable((Drawable) t);
        }
        mBtTitleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 左边的布局TextView
     *
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalLeft(T t) {
        mTvTitleLeft = (TextView) findViewById(R.id.tv_global_title_left);
        if (t instanceof Integer) {
            mTvTitleLeft.setText((Integer) t);
        } else if (t instanceof String) {
            mTvTitleLeft.setText((String) t);
        }
        mTvTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局TextView
     *
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalRight(T t) {
        mTvTitleRight = (TextView) findViewById(R.id.tv_global_title_right);
        if (t instanceof Integer) {
            mTvTitleRight.setText((Integer) t);
        } else if (t instanceof String) {
            mTvTitleRight.setText((String) t);
        }
        mTvTitleRight.setVisibility(View.VISIBLE);
    }


    /**
     * 中间的布局title的TextView
     *
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalTitleName(T t) {
        mTvTitleName = (TextView) findViewById(R.id.tv_global_title_name);
        if (t instanceof Integer) {
            mTvTitleName.setText((Integer) t);
        } else if (t instanceof String) {
            mTvTitleName.setText((String) t);
        }
        mTvTitleName.setVisibility(View.VISIBLE);
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (regEvent()) {
            EventBus.getDefault().unregister(this);
        }
        LibApplication.getInstance().getActivityManage().removeActivity(this);
    }


    @Override
    public void showLoadingComplete() {
        //Empty implementation
        dismissLoadingDialog();
    }

    @Override
    public Dialog showLoadingDialog() {

        return showLoadingDialog(getString(R.string.defult_load_content));
    }

    protected Dialog showLoadingDialog(String content) {
        mProgressDialog = LoadingUtil.createLoadingDialog(mContext, content);
        return mProgressDialog;
    }


    @Override
    public void dismissLoadingDialog() {
        LoadingUtil.closeDialog(mProgressDialog);
    }

    protected void toastShort(@StringRes int msg) {
        CustomToast.makeCustomText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastShort(@NonNull String msg) {
        CustomToast.makeCustomText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 事件点击前检查
     *
     * @param view           点击的view
     * @param action1        检查通过处理
     * @param func1          检查条件
     * @param windowDuration 时长
     * @param unit           时间标准
     */
    protected void filterClick(View view, Func1<Void, Boolean> func1, Action1<Void> action1, long windowDuration, TimeUnit unit) {
        RxView.clicks(view).throttleFirst(windowDuration, unit)
                .filter(func1)
                .subscribe(action1);
    }

    /**
     * 快速点击
     *
     * @param view
     * @param action1
     */
    protected void onClick(Object view, Action1<Void> action1) {
        View clickView = null;
        if (view instanceof View) {
            clickView = (View) view;
        } else {
            clickView = findViewById((Integer) view);
        }
        subscribeClick(clickView, action1, LibConstant.VIEW_THROTTLE_SHORT_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 短时间延迟
     */
    protected void onShortClick(Object view, Action1<Void> action1) {
        View clickView = null;
        if (view instanceof View) {
            clickView = (View) view;
        } else {
            clickView = findViewById((Integer) view);
        }
        subscribeClick(clickView, action1, LibConstant.VIEW_THROTTLE_MIDDLING_TIME, TimeUnit.SECONDS);
    }

    /**
     * 长时间延迟
     */
    protected void onLongClick(Object view, Action1<Void> action1) {
        View clickView = null;
        if (view instanceof View) {
            clickView = (View) view;
        } else {
            clickView = findViewById((Integer) view);
        }
        subscribeClick(clickView, action1, LibConstant.VIEW_THROTTLE_LONG_TIME, TimeUnit.SECONDS);
    }


    /**
     * 点击事件处理
     *
     * @param view
     * @param action1
     */
    private void subscribeClick(View view, Action1<Void> action1, long windowDuration, TimeUnit unit) {
        RxView.clicks(view)
                .throttleFirst(windowDuration, unit)
                .subscribe(action1);
    }


    /**
     * 指定销毁activity
     *
     * @param cls
     */
    protected void finishActivityByName(@NonNull Class<? extends Activity> cls) {
        for (Activity activity : LibApplication.getInstance().getActivityManage().allActivities) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
            }
        }
    }

    /**
     * 指定销毁activity
     */
    protected void finishAllActivity() {
        for (Activity activity : LibApplication.getInstance().getActivityManage().allActivities) {
            activity.finish();
        }
    }


    //-----------------------点击空白区域隐藏键盘-------------------------------
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //***************************************空页面方法*************************************

    protected void showEmptyView() {
        showEmptyView(getString(R.string.no_data));
    }

    protected void showEmptyView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_data);
    }

    protected void showErrorView() {
        showErrorView(getString(R.string.error_data));
    }

    protected void showErrorView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_net);
    }

    public void showEmptyOrErrorView(String text, int img) {

        if (emptyView == null) {
            emptyView = (ViewStub) findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        findViewById(R.id.iv_empty).setBackgroundResource(img);
        ((TextView) findViewById(R.id.tv_empty)).setText(text);
        findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPageClick();
            }
        });
    }

    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {

    }

    //***************************************空页面方法*********************************

}
