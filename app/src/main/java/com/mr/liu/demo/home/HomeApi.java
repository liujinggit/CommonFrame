package com.mr.liu.demo.home;


import com.mr.liu.modulebase.base.LibResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

import static com.mr.liu.demo.constent.Constent.*;

/**
 * Created by mrliu on 2019/1/2.
 * 此类用于:
 */

public interface HomeApi {
    @GET(LOGIN)
    Observable<LibResponse<String>> login(@Query("userType") String userType, @Query("mobile") String mobile, @Query("password") String password);
}
