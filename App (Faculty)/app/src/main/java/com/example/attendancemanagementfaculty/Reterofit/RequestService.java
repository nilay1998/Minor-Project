package com.example.attendancemanagementfaculty.Reterofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RequestService {

    @GET("getf")
    Call<Profile> reguestisClass();

    @GET("get")
    Call<Profile> requestGet();

    @GET("getall")
    Call<MyPojo> getStudents();

    @PUT("isclass")
    @FormUrlEncoded
    Call<Profile> updateClass(@Field("isClass") Boolean xyz);

    @PUT("addClass")
    @FormUrlEncoded
    Call<Profile> addClass(@Field("addClass") int xyz);

    @POST("faculty")
    @FormUrlEncoded
    Call<Profile> createUser(@Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password);

    @POST("loginf")
    @FormUrlEncoded
    Call<Profile> loginUser(@Field("email") String email,
                            @Field("password") String password);
}
