package com.mr.liu.demo.net;

import com.mr.liu.demo.BuildConfig;
import com.mr.liu.modulebase.base.LibApplication;
import com.mr.liu.modulebase.net.AddCookiesInterceptor;
import com.mr.liu.modulebase.net.CacheInterceptor;
import com.mr.liu.modulebase.net.ReceivedCookiesInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mrliu on 2019/1/2.
 * 此类用于:网络请求的管理类
 */

public class NetManager {

    private static final int CONNECT_TIME_OUT = 60;
    private static final int WRITE_TIME_OUT = 60;
    private static final int READ_TIME_OUT = 60;
    private Retrofit mRetrofit;

    public static NetManager getInstance() {
        return NetManager.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    protected NetManager() {
    }

    public <S> S create(Class<S> service) {
        return create(service, BuildConfig.HOST);
    }

    /**
     * 生成网络请求链接
     */
    public <S> S create(Class<S> service, String url) {
        Cache cache = new Cache(new File(LibApplication.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 50);

        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient.Builder clientBuilder = okHttpClient.newBuilder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
//                                    .addHeader("device", Build.MODEL)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .cache(cache)
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new CacheInterceptor())
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if (android.support.multidex.BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addNetworkInterceptor(logging);
        }

        okHttpClient = clientBuilder.build();
//
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        return mRetrofit.create(service);
    }
}
