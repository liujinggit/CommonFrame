package com.mr.liu.demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mr.liu.modulebase.base.LibBaseFragment;
import com.mr.liu.modulebase.base.LibPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public abstract class BaseFragment<P extends LibPresenter> extends LibBaseFragment {

    protected Unbinder mUnbinder;
    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mPresenter == null) mPresenter = getPresenter();
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        initData();
        initListener();
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.unSubscribeTasks();
        mPresenter = null;
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected abstract P getPresenter();
}
