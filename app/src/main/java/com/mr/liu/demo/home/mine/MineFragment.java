package com.mr.liu.demo.home.mine;

import android.os.Bundle;

import android.view.View;
import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseFragment;
import com.mr.liu.demo.widget.utils.StatusbarUtil;
import com.mr.liu.modulebase.base.LibPresenter;
import com.mr.liu.modulebase.utils.ScreenUtils;


/**
 * Created by mrliu on 2019/1/7.
 * 此类用于:
 */

public class MineFragment extends BaseFragment implements MineInterface {

    private static MineFragment instance;


    public static MineFragment newInstance() {
        Bundle bundle = new Bundle();
        if (instance == null) {
            instance = new MineFragment();
        }
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected LibPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
//        StatusbarUtil.setTransparentForImageView(getActivity(), null);
//        int statusBarHeight = ScreenUtils.getStatusBarHeight(getActivity());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


    }

}
