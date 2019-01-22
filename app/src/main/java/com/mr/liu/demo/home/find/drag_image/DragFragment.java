package com.mr.liu.demo.home.find.drag_image;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseFragment;
import com.mr.liu.modulebase.base.LibPresenter;
import com.mr.liu.modulebase.widget.circle.DragRelativeLayout;
import com.mr.liu.modulebase.widget.circle.OnDragListener;

import butterknife.BindView;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:
 */

public class DragFragment extends BaseFragment {

    public static final String REGION = "region";
    public static final String POSITION = "position";
    public static final String IMAGE_URL = "image_url";

    @BindView(R.id.drag_rl)
    public DragRelativeLayout mDragLayout;
    @BindView(R.id.pdv_photo)
    PhotoDraweeView mPhotoDraweeView ;

    public static DragFragment newInstance(int[] globalRect, String imageUrl, int position) {
        Bundle bundle = new Bundle();
        bundle.putIntArray(REGION, globalRect);
        bundle.putString(IMAGE_URL, imageUrl);
        bundle.putInt(POSITION, position);
        DragFragment instance = new DragFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected LibPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_drag_fragment;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int[] loc = bundle.getIntArray(REGION);
            int pos = bundle.getInt(POSITION);
            String imageUrl = bundle.getString(IMAGE_URL);
            if (loc != null) {
                mDragLayout.setTransitionsRegion(loc[0], loc[1], loc[2], loc[3], loc[4], loc[5]);
            }
            mDragLayout.startTransitions();
            mPhotoDraweeView.setPhotoUri(Uri.parse(imageUrl));
        }


        mPhotoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });



        mDragLayout.setOnDragListener(new OnDragListener() {
            @Override
            public void onStartDrag() {
                super.onStartDrag();
                mDragLayout.setBackgroundColor(00000000);
            }

            @Override
            public void onStartEnter(boolean outOfBound) {
                super.onStartEnter(outOfBound);
            }

            @Override
            public void onRelease(boolean isResume) {
                super.onRelease(isResume);
                if(isResume){
                    mDragLayout.setBackgroundColor(getResources().getColor(R.color.black_content));
                }else {
                    mDragLayout.setBackgroundColor(00000000);
                }

            }

            @Override
            public void onEndExit() {
                super.onEndExit();
                finish();
            }

            @Override
            public void onEndEnter() {
                super.onEndEnter();
            }

            @Override
            public void onStartExit(boolean outOfBound) {
                super.onStartExit(outOfBound);
            }

            @Override
            public void onEndResume() {
                super.onEndResume();
            }
        });

    }

    private void finish() {
        ((DragImageListActivity)getActivity()).finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
