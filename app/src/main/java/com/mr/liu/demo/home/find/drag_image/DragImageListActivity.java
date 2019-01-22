package com.mr.liu.demo.home.find.drag_image;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseActivity;
import com.mr.liu.demo.bean.ScrollToPositionEvent;
import com.mr.liu.modulebase.base.LibPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:仿微信朋友圈拖拽图片
 */

public class DragImageListActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    public static final String POSITION = "position";
    public static final String LIST_IMAGE = "image_list";
    public static final String REGION = "region";

    private int mPosition;
    private ArrayList<String> mStringList = new ArrayList<>();
    private int[] mGlobalRect = new int[5];

    private List<Fragment> mFragments = new ArrayList<>();
    private DragFragment mDragFragment;

    @Override
    protected void parseIntentData(Intent intent, boolean isFromNewIntent) {
        super.parseIntentData(intent, isFromNewIntent);
        mPosition = intent.getIntExtra(POSITION, 0);
        mStringList = intent.getStringArrayListExtra(LIST_IMAGE);
        mGlobalRect = intent.getIntArrayExtra(REGION);
    }

    @Override
    protected LibPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_drag_image;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < mStringList.size(); i++) {
            mDragFragment = DragFragment.newInstance(mGlobalRect, mStringList.get(mPosition), mPosition);
            mFragments.add(mDragFragment);
        }

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new AdapterFragment(getSupportFragmentManager(), mFragments));
        mViewPager.setCurrentItem(mPosition);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mDragFragment.mDragLayout!=null){
                    EventBus.getDefault().post(new ScrollToPositionEvent(position, mDragFragment.mDragLayout.getDuration(), true, new ScrollToPositionEvent.OnRegionListener() {
                        @Override
                        public void onRegion(int l, int t, int r, int b, int w, int h) {
                            mDragFragment.mDragLayout.setTransitionsRegion(l, t, r, b, w, h);
                        }
                    }));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    public class AdapterFragment extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public AdapterFragment(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {//必须实现
            return mFragments.get(position);
        }

        @Override
        public int getCount() {//必须实现
            return mFragments.size();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFragments != null && !mFragments.isEmpty()) mFragments.clear();
    }
}
