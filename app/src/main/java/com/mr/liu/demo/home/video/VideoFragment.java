package com.mr.liu.demo.home.video;

import android.os.Bundle;
import android.view.View;

import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseFragment;
import com.mr.liu.modulebase.base.LibPresenter;

/**
 * Created by mrliu on 2019/1/9.
 * 此类用于:
 */

public class VideoFragment extends BaseFragment {
    private static VideoFragment instance;

    /**
     * 获取fragment单例
     */
    public static VideoFragment newInstance(){
        Bundle bundle = new Bundle();
        if(instance==null){
            instance = new VideoFragment();
        }
        instance.setArguments(bundle);
        return instance ;
    }
    @Override
    protected LibPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
