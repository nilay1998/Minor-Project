package com.example.attendancemanagement.Reterofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestService {

    @GET("get")
    Call<Profile> requestGet();


    @POST("user")
    @FormUrlEncoded
    Call<Profile> createUser(@Field("id") String id,
                             @Field("name") String name,
                             @Field("email") String email,
                             @Field("rollNumber") String rollNumber,
                             @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<Profile> loginUser(@Field("email") String email,
                            @Field("password") String password);
}
