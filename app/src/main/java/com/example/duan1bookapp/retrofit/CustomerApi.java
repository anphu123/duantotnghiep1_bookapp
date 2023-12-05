package com.example.duan1bookapp.retrofit;

import com.example.duan1bookapp.models.Customer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CustomerApi {
    @Headers("Content-Type: application/json")
    @POST("/api/v1/customer/dang-ky")
    Call<Customer> save(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/customer/cap-nhap")
    Call<Customer> update(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/customer/dang-nhap")
    Call<Customer> login(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/customer/cap-nhap-profile/{customerid}/{customerName}/{customerPassword}/{customerbirthDate}/{avatar_url}/{addressid}/{street}/{city}")
    Call<String> update(@Path("customerid")int customerid,
                         @Path("customerName")String customerName,
                         @Path("customerPassword")String customerPassword,
                         @Path("customerbirthDate")String customerbirthDate,
                         @Path("avatar_url")String avatar_url,
                         @Path("addressid")int addressid,
                         @Path("street")String street,
                         @Path("city")String city
    );

}

