package com.example.attendancemanagement.Reterofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RequestService {
    @POST("register")
    @FormUrlEncoded
    Call<Profile> createUser(@Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("phone") String phone );

    @POST("login")
    @FormUrlEncoded
    Call<Profile> loginUser(@Field("email") String email,
                            @Field("password") String password);
}
