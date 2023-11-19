package com.example.duan1bookapp.retrofit;

import com.example.duan1bookapp.models.Chapter;
import com.example.duan1bookapp.models.Coin;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.models.Link;
import com.example.duan1bookapp.models.MangaComment;
import com.example.duan1bookapp.models.Product;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

}

