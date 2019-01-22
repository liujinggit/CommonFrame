package com.mr.liu.modulebase.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
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

import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:基类的Fragment
 */

public abstract class LibBaseFragment extends Fragment implements LibInterface{

    protected FragmentActivity mContext;
    protected Activity mActivity;
    private Dialog mProgressDialog;
    protected TextView mTvTitleLeft;
    protected ImageButton mBtTitleLeft;
    protected TextView mTvTitleRight;
    protected TextView mTvTitleName;
    protected ImageButton mBtTitleRight;
    protected View rootView;

    private ViewStub emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (LibBaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.base_fragment_base, container, false);
        LayoutInflater getLayoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ((ViewGroup) rootView.findViewById(R.id.fl_content)).addView(getLayoutInflater.inflate(getLayoutId(), null));
        if (regEvent()) {
            EventBus.getDefault().register(this);
        }
        return rootView ;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void showLoadingComplete() {
        //Empty implementation
        dismissLoadingDialog();
    }

    @Override
    public Dialog showLoadingDialog() {
        return showLoadingDialog(getResources().getString(R.string.defult_load_content));
    }


    protected Dialog showLoadingDialog(String content) {
        mProgressDialog = LoadingUtil.createLoadingDialog(mContext,content);
        return mProgressDialog;
    }

    @Override
    public void dismissLoadingDialog() {
        LoadingUtil.closeDialog(mProgressDialog);
    }
    /**
     * 左边的布局ImageButton
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalLeft(T t){
        mBtTitleLeft = (ImageButton) rootView.findViewById(R.id.bt_global_title_left);
        if(t instanceof Integer){
            mBtTitleLeft.setImageResource((Integer) t);
        }else if(t instanceof Drawable){
            mBtTitleLeft.setImageDrawable((Drawable) t);
        }
        mBtTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局ImageButton
     * @param t
     * @param <T>
     */
    protected <T> void setBtGlobalRight(T t){
        mBtTitleRight = (ImageButton) rootView.findViewById(R.id.bt_global_title_right);
        if(t instanceof Integer){
            mBtTitleRight.setImageResource((Integer) t);
        }else if(t instanceof Drawable){
            mBtTitleRight.setImageDrawable((Drawable) t);
        }
        mBtTitleRight.setVisibility(View.VISIBLE);
    }

    /**
     * 左边的布局TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalLeft(T t){
        mTvTitleLeft = (TextView) rootView.findViewById(R.id.tv_global_title_left);
        if(t instanceof Integer){
            mTvTitleLeft.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleLeft.setText((String)t);
        }
        mTvTitleLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 右边的布局TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalRight(T t){
        mTvTitleRight = (TextView) rootView.findViewById(R.id.tv_global_title_right);
        if(t instanceof Integer){
            mTvTitleRight.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleRight.setText((String)t);
        }
        mTvTitleRight.setVisibility(View.VISIBLE);
    }


    /**
     * 中间的布局title的TextView
     * @param t
     * @param <T>
     */
    protected <T> void setTvGlobalTitleName(T t){
        mTvTitleName = (TextView) rootView.findViewById(R.id.tv_global_title_name);
        if(t instanceof Integer){
            mTvTitleName.setText((Integer) t);
        }else if(t instanceof String){
            mTvTitleName.setText((String)t);
        }
        mTvTitleName.setVisibility(View.VISIBLE);
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
//        toastShort(R.string.msg_unknown_error);
    }

    protected void toastShort(@StringRes int msg){
        CustomToast.makeCustomText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toastShort(@NonNull String msg){
        CustomToast.makeCustomText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 事件点击前检查
     * @param view 点击的view
     * @param action1 检查通过处理
     * @param func1 检查条件
     * @param windowDuration 时长
     * @param unit 时间标准
     */
    protected void filterClick(View view, Func1<Void, Boolean> func1, Action1<Void> action1, long windowDuration, TimeUnit unit){
        RxView.clicks(view).throttleFirst(windowDuration, unit)
                .filter(func1)
                .subscribe(action1);
    }

    /**
     * 快速点击
     * @param view
     * @param action1
     */
    protected void onClick(Object view,Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1, LibConstant.VIEW_THROTTLE_SHORT_TIME,TimeUnit.MILLISECONDS);
    }

    /**
     * 短时间延迟
     */
    protected void onShortClick(Object view, Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1, LibConstant.VIEW_THROTTLE_MIDDLING_TIME,TimeUnit.SECONDS);
    }

    /**
     * 长时间延迟
     */
    protected void onLongClick(Object view, Action1<Void> action1){
        View clickView = null;
        if(view instanceof View){
            clickView = (View) view;
        }else {
            clickView = mContext.findViewById((Integer) view);
        }
        subscribeClick(clickView,action1,LibConstant.VIEW_THROTTLE_LONG_TIME,TimeUnit.SECONDS);
    }


    /**
     * 点击事件处理
     * @param view
     * @param action1
     */
    private void subscribeClick(View view, Action1<Void> action1,long windowDuration, TimeUnit unit){
        RxView.clicks(view)
                .throttleFirst(windowDuration,unit)
                .subscribe(action1);
    }


    @Override
    public void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
        if (regEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 需要接收事件 重新该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }



    //***************************************空页面方法*************************************
    protected void showEmptyView() {
        showEmptyOrErrorView(getString(R.string.no_data), R.drawable.bg_no_data);
    }

    protected void showEmptyView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_data);
    }


    protected void showErrorView() {
        showEmptyOrErrorView(getString(R.string.error_data), R.drawable.bg_no_net);
    }
    protected void showErrorView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_net);
    }

    public void showEmptyOrErrorView(String text, int img) {
        if (emptyView == null) {
            emptyView = rootView.findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.iv_empty).setBackgroundResource(img);
        ((TextView) rootView.findViewById(R.id.tv_empty)).setText(text);
        rootView.findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
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
