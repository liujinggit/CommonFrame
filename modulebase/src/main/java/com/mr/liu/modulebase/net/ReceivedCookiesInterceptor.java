package com.mr.liu.modulebase.net;

import com.mr.liu.modulebase.helper.SPUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:第一次请求接收cookie
 */

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPUtils.save("cookie", cookies);
        }

        return originalResponse;
    }
}
