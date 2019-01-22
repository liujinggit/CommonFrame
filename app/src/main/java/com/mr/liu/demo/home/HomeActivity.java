package com.mr.liu.demo.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toast;

import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseActivity;
import com.mr.liu.demo.home.find.FindFragment;
import com.mr.liu.demo.home.mine.MineFragment;
import com.mr.liu.demo.home.report.ReportFragment;
import com.mr.liu.demo.home.video.VideoFragment;
import com.mr.liu.demo.widget.behavior.BottomNavigationViewHelper;

import butterknife.BindView;

/**
 * Created by mrliu on 2018/12/18.
 * 此类用于:主页
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeInterface {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigationView;


    //fragment添加标识
    private static final int FRAGMENT_FIND = 0;
    private static final int FRAGMENT_REPORT = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MINE = 3;
    //保存fragment
    private static final String POSITION = "position";
    private int position;

    private FindFragment mFindFragment;
    private ReportFragment mReportFragment;
    private VideoFragment mVideoFragment;
    private MineFragment mMineFragment;

    private long exitTime = 0;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt(POSITION);
        mFindFragment = (FindFragment) getSupportFragmentManager().findFragmentByTag(FindFragment.class.getName());
        mReportFragment = (ReportFragment) getSupportFragmentManager().findFragmentByTag(ReportFragment.class.getName());
        mVideoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag(VideoFragment.class.getName());
        mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag(MineFragment.class.getName());
        showFragment(position);
    }

    /**
     * 展示fragment
     *
     * @param position 展示fragment的索引
     */
    private void showFragment(int position) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        this.position = position;

        switch (position) {
            case FRAGMENT_FIND:
                if (mFindFragment == null) {
                    mFindFragment = FindFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mFindFragment, FindFragment.class.getName());
                } else {
                    fragmentTransaction.show(mFindFragment);
                }
                break;
            case FRAGMENT_REPORT:
                if (mReportFragment == null) {
                    mReportFragment = ReportFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mReportFragment, ReportFragment.class.getName());
                } else {
                    fragmentTransaction.show(mReportFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                if (mVideoFragment == null) {
                    mVideoFragment = VideoFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mVideoFragment, VideoFragment.class.getName());
                } else {
                    fragmentTransaction.show(mVideoFragment);
                }
                break;
            case FRAGMENT_MINE:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                    fragmentTransaction.add(R.id.container, mMineFragment, MineFragment.class.getName());
                } else {
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }

        fragmentTransaction.commit();
    }

    /**
     * 隐藏fragment
     *
     * @param transaction 事务
     */
    private void hideFragment(FragmentTransaction transaction) {
        // 如果不为空，就先隐藏起来
        if (mFindFragment != null) {
            transaction.hide(mFindFragment);
        }
        if (mReportFragment != null) {
            transaction.hide(mReportFragment);
        }
        if (mVideoFragment != null) {
            transaction.hide(mVideoFragment);
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment);
        }
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }


    @Override
    protected void initView() {
        setSwipeBackEnable(false);

        showFragment(FRAGMENT_FIND);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_find:
                    showFragment(FRAGMENT_FIND);
//                        doubleClick(FRAGMENT_NEWS);
                    break;
                case R.id.action_report:
                    showFragment(FRAGMENT_REPORT);
//                        doubleClick(FRAGMENT_PHOTO);
                    break;
                case R.id.action_video:
                    showFragment(FRAGMENT_VIDEO);
//                        doubleClick(FRAGMENT_VIDEO);
                    break;
                case R.id.action_mine:
                    showFragment(FRAGMENT_MINE);
                    break;
            }
            return true;
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void success() {

    }

    @Override
    public void failure() {

    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - exitTime) < 2000) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, R.string.double_click_exit, Toast.LENGTH_SHORT).show();
            exitTime = currentTime;
        }
    }
}
