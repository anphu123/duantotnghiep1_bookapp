package com.example.duan1bookapp.retrofit;

import com.example.duan1bookapp.models.Coin;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {
    @Headers("Content-Type: application/json")
    @POST("api/v1/customer/order/create")
    Call<Order> CreateOrder(@Body Order order);


    @Headers("Content-Type: application/json")
    @POST("api/v1/customer/order/update/{momo}/{id}")
    Call<Order> verify(@Path("momo")String momo, @Path("id")int id);

    @Headers("Content-Type: application/json")
    @POST("api/v2/coins/order/{coin_id}/{value}")
    Call<Coin> addcoin(@Path("coin_id")int coin_id, @Path("value")int value);

    @PATCH("/api/v2/coins/{customerid}/subtract")
    Call<Integer> subtractPrice(
            @Path("customerid") int customerId,
            @Query("amount") int amount
    );
}

