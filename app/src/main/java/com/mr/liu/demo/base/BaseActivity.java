package com.mr.liu.demo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mr.liu.modulebase.base.LibBaseActivity;
import com.mr.liu.modulebase.base.LibPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:activity基类
 */

public abstract class BaseActivity<P extends LibPresenter> extends LibBaseActivity {

    protected Unbinder mBinder;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntentData(getIntent(), false);
        mBinder = ButterKnife.bind(this);
        //创建present
        if (mPresenter == null) mPresenter = getPresenter();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.unSubscribeTasks();
            mPresenter = null;
        }
        if (mBinder != null) {
            mBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // Fragment 逐个出栈
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //refresh intent data!
        parseIntentData(intent, true);
    }

    protected void parseIntentData(Intent intent, boolean isFromNewIntent) {
        //empty implementation

    }


    protected abstract P getPresenter();
}
