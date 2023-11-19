package com.example.duan1bookapp.retrofit;

import com.example.duan1bookapp.models.Chapter;
import com.example.duan1bookapp.models.Link;
import com.example.duan1bookapp.models.MangaComment;
import com.example.duan1bookapp.models.Product;
import com.example.duan1bookapp.models.slideShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IComicAPI {

    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/comic")
    Call<List<Product>> getComicList();

    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/category/{categoryname}")
    Call<List<Product>> getComicByTypeList(@Path("categoryname")String categoryname);

    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/comic")
    Call<List<slideShow>> getShowData();
    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/chapter/{mangaid}")
    Call<List<Chapter>> getChapterList(@Path("mangaid")int mangaid);

    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/link/{linkid}")
    Call<List<Link>> getPageList(@Path("linkid")int linkid);

    @Headers("Content-Type: application/json")
    @POST("/api/v1/product/{mangaid}/comment/addNewsComment")
    Call<MangaComment> createCommentOnGist(@Path("mangaid") int mangaid, @Body MangaComment mangaComment);
    @Headers("Content-Type: application/json")
    @GET("/api/v1/product/{id}/comments/get")
    Call<List<MangaComment>> getGistCommentsById(@Path("id") int mangaid, @Query("page") int pageNum);

}
