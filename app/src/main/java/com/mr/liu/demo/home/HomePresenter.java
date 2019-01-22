package com.mr.liu.demo.home;

import com.mr.liu.demo.net.NetManager;
import com.mr.liu.modulebase.base.LibObserver;
import com.mr.liu.modulebase.base.LibPresenter;
import com.mr.liu.modulebase.base.LibResponse;
import com.mr.liu.modulebase.utils.MD5Util;
import com.mr.liu.modulebase.utils.MLog;

import rx.Subscription;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public class HomePresenter extends LibPresenter<HomeInterface> {

    protected HomePresenter(HomeInterface uiInterface) {
        super(uiInterface);
    }

    /**
     * 测试登录接口
     */
    public void login(){
        String s = MD5Util.string2MD5("abcd1234");
        Subscription subscribe = NetManager.getInstance().create(HomeApi.class).login("GUARDIAN", "18310812141", s)
                .compose(this.applyAsySchedulers())
                .subscribe(new LibObserver<LibResponse<String>>(getUiInterface()) {
                    @Override
                    public void onSuccess(LibResponse<String> response) {
                        MLog.e("HomePresenter","onsuccess");
                    }

                    @Override
                    protected void onDataFailure(LibResponse<String> response) {
                        super.onDataFailure(response);
                        MLog.e("HomePresenter","onDataFailure");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        MLog.e("HomePresenter","onError");
                    }
                });
        addSubscription(subscribe);

    }
}
