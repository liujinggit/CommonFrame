package com.mr.liu.demo.home.report;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mr.liu.demo.R;
import com.mr.liu.demo.base.BaseFragment;
import com.mr.liu.demo.home.report.bigimage.BigImageListActivity;
import com.mr.liu.modulebase.base.LibPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mrliu on 2019/1/7.
 * 此类用于:仿新浪微博拖动
 */

public class ReportFragment extends BaseFragment implements ReportInterface {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private List<String> mList = new ArrayList<>();

    private static ReportFragment instance;

    public static ReportFragment newInstance() {
        Bundle bundle = new Bundle();
        if (instance == null) {
            instance = new ReportFragment();
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
        return R.layout.fragment_report;
    }

    @Override
    protected void initView(View view) {
        for (int i = 0; i < 10; i++) {
            mList.add("http://images-dev.iyunrui.com.cn/20190115/1326a1e0-156a-4cc8-becd-c7a9fe165de2.jpg");
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(new ReportAdapter());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    private class ReportAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_report, parent, false);
            return new ReportHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ReportHolder) holder).disPlay(mList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class ReportHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mSimpleDraweeView;

        public ReportHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.simple_draw_view);
        }

        public void disPlay(String image, final int position) {
            mSimpleDraweeView.setImageURI(Uri.parse(image));

            onShortClick(mSimpleDraweeView, aVoid ->
                    startActivity(BigImageListActivity.createIntent(getContext(), position, (ArrayList<String>) mList)));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!mList.isEmpty()) mList.clear();
    }
}
