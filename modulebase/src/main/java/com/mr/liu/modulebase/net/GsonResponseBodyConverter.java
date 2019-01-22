package com.mr.liu.modulebase.net;

import com.google.gson.Gson;
import com.mr.liu.modulebase.base.LibResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public class GsonResponseBodyConverter <T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        LibResponse httpResult = gson.fromJson(response, LibResponse.class);
        return gson.fromJson(response, type);
    }
}
