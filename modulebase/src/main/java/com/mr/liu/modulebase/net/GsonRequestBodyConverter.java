package com.mr.liu.modulebase.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.mr.liu.modulebase.base.request.BaseParam;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * Created by mrliu on 2018/12/29.
 * 此类用于:
 */

public class GsonRequestBodyConverter <T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> type;


    public GsonRequestBodyConverter(Gson gson, TypeAdapter<T> type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        BaseParam baseParam = new BaseParam();
        if (value instanceof BaseParam) {
            baseParam.setData(value);
        }

        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        type.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
