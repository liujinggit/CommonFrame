package com.mr.liu.demo.home.report.bigimage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseActivity;
import com.mr.liu.modulebase.base.LibPresenter;
import com.mr.liu.modulebase.widget.weibodrag.PhotoDragHelper;
import com.mr.liu.modulebase.widget.weibodrag.PhotoDragRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by mrliu on 2019/1/21.
 * 此类用于:
 */

public class BigImageListActivity extends BaseActivity {
    public static final String POSITION = "position";
    public static final String LIST_IMAGE = "image_list";

    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;

    private int mPosition;
    private ArrayList<String> mStringList = new ArrayList<>();

    public static Intent createIntent(Context context, int position, ArrayList<String> imageList) {
        Intent intent = new Intent(context, BigImageListActivity.class);
        intent.putExtra(POSITION, position);
        intent.putStringArrayListExtra(LIST_IMAGE, imageList);
        return intent;
    }

    @Override
    protected void parseIntentData(Intent intent, boolean isFromNewIntent) {
        super.parseIntentData(intent, isFromNewIntent);
        mPosition = intent.getIntExtra(POSITION, 0);
        mStringList = intent.getStringArrayListExtra(LIST_IMAGE);
    }

    @Override
    protected LibPresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_big_image;
    }

    @Override
    protected void initView() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(mPosition);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ImageListAdapter());
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private class ImageListAdapter extends RecyclerView.Adapter<ImageListHolder> {

        @Override
        public ImageListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(BigImageListActivity.this).inflate(R.layout.item_list_image, parent, false);
            return new ImageListHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageListHolder holder, int position) {
            holder.disPlay(mStringList.get(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }

    private class ImageListHolder extends RecyclerView.ViewHolder {
        private final PhotoDragRelativeLayout mPdrLayout;
        private final PhotoDraweeView mPdvView;

        private ImageListHolder(View itemView) {
            super(itemView);
            mPdrLayout = itemView.findViewById(R.id.pdr_content);
            mPdvView = itemView.findViewById(R.id.pdv_photo);
        }

        private void disPlay(String s) {
            mPdrLayout.setDragListener(new PhotoDragHelper().setOnDragListener(new PhotoDragHelper.OnDragListener() {
                @Override
                public void onAlpha(float alpha) {
                    mPdrLayout.setAlpha(alpha);
                }

                @Override
                public View getDragView() {
                    return mPdvView;
                }

                @Override
                public void onAnimationEnd(boolean mSlop) {
                    if (mSlop) {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }
            }));

            mPdvView.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });

            mPdvView.setPhotoUri(Uri.parse(s));

        }
    }
}
