package com.mr.liu.demo.home.find;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseFragment;
import com.mr.liu.demo.bean.ScrollToPositionEvent;
import com.mr.liu.demo.home.find.drag_image.DragImageListActivity;
import com.mr.liu.modulebase.base.LibPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by mrliu on 2019/1/7.
 * 此类用于:
 */

public class FindFragment extends BaseFragment implements FindInterface {
    @BindView(R.id.find_recycler)
    RecyclerView mRecyclerView;

    private List<String> mList = new ArrayList<>();

    private static FindFragment instance;
    private FindAdapter mFindAdapter;

    /**
     * 获取fragment单例
     */
    public static FindFragment newInstance() {
        Bundle bundle = new Bundle();
        if (instance == null) {
            instance = new FindFragment();
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
        return R.layout.fragment_find;
    }

    @Override
    protected void initView(View view) {

        for (int i = 0; i < 9; i++) {
            mList.add("http://images-dev.iyunrui.com.cn/20190115/1326a1e0-156a-4cc8-becd-c7a9fe165de2.jpg");
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int pos = parent.getChildAdapterPosition(view);
                outRect.top = (pos / 3 == 0) ? 0 : 2;
                if (pos % 3 == 0) {
                    outRect.right = 1;
                } else {
                    outRect.left = 1;
                }
            }
        });
        mFindAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mFindAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }


    private class FindAdapter extends RecyclerView.Adapter<FindHolder> {

        @Override
        public FindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_find, parent, false);
            return new FindHolder(view);
        }

        @Override
        public void onBindViewHolder(FindHolder holder, int position) {
            holder.disPlay(mList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class FindHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mSimpleDraweeView;

        public FindHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.iv_find);
        }

        public void disPlay(String image, final int position) {
            mSimpleDraweeView.setImageURI(Uri.parse(image));
            onShortClick(itemView, new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Rect globalRect = new Rect();
                    itemView.getGlobalVisibleRect(globalRect);
                    Intent intent = new Intent(getContext(),DragImageListActivity.class);
                    intent.putExtra(DragImageListActivity.POSITION, position);
                    intent.putStringArrayListExtra(DragImageListActivity.LIST_IMAGE, (ArrayList<String>) mList);
                    intent.putExtra(DragImageListActivity.REGION, new int[]{globalRect.left, globalRect.top, globalRect.right, globalRect.bottom, itemView.getWidth(), itemView.getHeight()});
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected boolean regEvent() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final ScrollToPositionEvent event) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToPosition(event.position);
                GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
                View childView = layoutManager.findViewByPosition(event.position);
                if (childView != null && event.listener != null) {
                    Rect rect = new Rect();
                    childView.getGlobalVisibleRect(rect);
                    event.listener.onRegion(rect.left, rect.top, rect.right, rect.bottom, childView.getWidth(), childView.getHeight());
                }
            }
        }, event.delayEnable ? event.delayDuration : 0);
    }

    // 列表滚动到指定位置
    private void moveToPosition(int n) {
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
            int firstItem = gridLayoutManager.findFirstVisibleItemPosition();
            int lastItem = gridLayoutManager.findLastVisibleItemPosition();
            // 然后区分情况
            if (n <= firstItem) {
                // 当要置顶的项在当前显示的第一个项的前面时
                int top = mRecyclerView.getChildAt(0).getTop();
                mRecyclerView.scrollBy(0, top);
            } else if (n <= lastItem) {
                // 当要置顶的项已经在屏幕上显示时
                mRecyclerView.scrollToPosition(n);
            } else {
                // 当要置顶的项在当前显示的最后一项的后面时
                mRecyclerView.scrollToPosition(n);
            }
        }
    }
}
