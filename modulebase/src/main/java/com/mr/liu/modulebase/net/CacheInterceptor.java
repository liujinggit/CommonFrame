package com.mr.liu.modulebase.net;

import com.mr.liu.modulebase.base.LibApplication;
import com.mr.liu.modulebase.utils.NetWatchdog;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:网络缓存
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWatchdog.hasNet(LibApplication.getInstance())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        Response originalResponse = chain.proceed(request);
        if (NetWatchdog.hasNet(LibApplication.getInstance())) {
            // 有网络时 设置缓存为默认值
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma") //Pragma:no-cache。在HTTP/1.1协议中，它的含义和Cache-Control:no-cache相同。为了确保缓存生效
                    .build();
        } else {
            // 无网络时 设置超时为1周
            int maxStale = 60 * 60 * 24 * 7;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    }

}
