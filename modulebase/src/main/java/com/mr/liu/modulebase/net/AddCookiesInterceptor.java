package com.mr.liu.modulebase.net;

import android.util.Log;

import com.mr.liu.modulebase.helper.SPUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:添加cookie
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        HashSet<String> preferences = (HashSet<String>) SPUtils.get("cookie", null);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        //服务器不压缩
        builder.addHeader("Accept-Encoding", "identity");
        return chain.proceed(builder.build());
    }
}
