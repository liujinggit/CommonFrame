package com.mr.liu.modulebase.base;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.mr.liu.modulebase.helper.SPUtils;
import com.mr.liu.modulebase.utils.MLog;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:网络请求返回处理(成功,失败,错误)
 */

public abstract class LibObserver<E extends LibResponse> implements Observer<E> {
    protected final String LOG_TAG = getClass().getSimpleName();

    private final LibInterface mUiInterface;

    public LibObserver(LibInterface baseUiInterface) {
        mUiInterface = baseUiInterface;
    }

    @Override
    public void onCompleted() {
        mUiInterface.showLoadingComplete();
    }

    @Override
    public void onError(Throwable throwable) {
        MLog.e("BaseObserver", "Request Error!");
        handleError(throwable, mUiInterface, LOG_TAG);
    }

    /**
     * 按照通用规则解析和处理数据请求时发生的错误。这个方法在执行支付等非标准的REST请求时很有用。
     */
    public void handleError(Throwable throwable, LibInterface mUiInterface, String LOG_TAG) {
        mUiInterface.showLoadingComplete();
        if (throwable == null) {
            mUiInterface.showUnknownException();
            return;
        }

        //分为以下几类问题：网络连接，数据解析，客户端出错【空指针等】，服务器内部错误
        if (throwable instanceof SocketTimeoutException || throwable
                instanceof ConnectException || throwable instanceof UnknownHostException) {
            mUiInterface.showNetworkException();
        } else if ((throwable instanceof JsonSyntaxException) || (throwable instanceof
                NumberFormatException) || (throwable instanceof MalformedJsonException)) {
            mUiInterface.showDataException("数据解析出错");
        } else if ((throwable instanceof HttpException)) {
//            mUiInterface.showDataException("服务器错误(" + ((HttpException) throwable).code()+")");
//            //自动上报这个异常
//            MLog.e(LOG_TAG, "Error while performing response!");
            parseException(throwable);

        } else if (throwable instanceof NullPointerException) {
            mUiInterface.showDataException("客户端开小差了，攻城狮正在修复中...");
            MLog.e(LOG_TAG, "Error while performing response!", throwable);
        } else {
            mUiInterface.showUnknownException();
        }
    }

    /**
     * 解析异常
     *
     * @param throwable
     */
    private void parseException(Throwable throwable) {
        String message = null;
        try {
            message = ((HttpException) throwable).response().errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(message)) {
            LibResponse response = new Gson().fromJson(message, LibResponse.class);
            response.setSuccess(true);
            if (response.getMessage().contains("\"errors.user.login\"") || response.getMessage().contains("未登陆异常")) {
                response.setMessage(response.getMessage().substring(0, response.getMessage().lastIndexOf("\r\n Error Code")));
                SPUtils.clear();

            } else if (response.getMessage().contains("Error Code")) {
                response.setMessage(response.getMessage().substring(0, response.getMessage().lastIndexOf("\r\n Error Code")));
            }
            onNext((E) response);
        }

    }

    @Override
    public void onNext(E response) {
        if (response.isOk()) {
            onSuccess(response);
        } else {
//            if (mUiInterface instanceof BaseActivity || mUiInterface instanceof BaseFragment){
//                final BaseActivity activity;
//                if (mUiInterface instanceof BaseFragment){
//                    activity = (BaseActivity) ((BaseFragment)mUiInterface).getActivity();
//                }
//                else{
//                    activity = (BaseActivity)mUiInterface;
//                }
//                if(mUiInterface instanceof SplashActivity){
//
//                }else if(mUiInterface instanceof LoginActivity){
//                    onDataFailure(response);
//                }
//
//                if((response.getMsg().equals("missing token")||response.getMsg().equals("会话已过期")) && !(mUiInterface instanceof SplashActivity)){
//                    final AlertDialog alertDialog = new AlertDialog.Builder(activity)
//                            .setMessage("您的账号在其他地方已登录！请重新登录")
//                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    activity.finishAllActivity();
//                                    activity.startActivity(LoginActivity.createIntent(activity));
//                                    dialog.dismiss();
//                                }
//                            })
//                            .create();
//                    alertDialog.setCancelable(false);
//                    alertDialog.show();
//                    return;
//                }else{
//                    onDataFailure(response);
//                }
//            }
            onDataFailure(response);
        }
    }

    public abstract void onSuccess(E response);

    protected void onDataFailure(E response) {
        String msg = response.getMessage();
        MLog.w(LOG_TAG, "request data but get failure:" + msg);
        if (!TextUtils.isEmpty(msg)) {
            mUiInterface.showDataException(response.getMessage());
        } else {
            mUiInterface.showUnknownException();
        }
    }

    /**
     * Create a new silence, non-leak observer.
     */
    public static <T> Observer<T> silenceObserver() {
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                //Empty
            }

            @Override
            public void onError(Throwable e) {
                //Empty
            }

            @Override
            public void onNext(T t) {
                //Empty
            }
        };
    }
}
