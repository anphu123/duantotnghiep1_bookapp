package com.example.duan1bookapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemProductListener;
import com.example.duan1bookapp.adapters.AdapterProduct;
import com.example.duan1bookapp.models.Product;
import com.example.duan1bookapp.retrofit.IComicAPI;
import com.example.duan1bookapp.retrofit.RetrofitService;
import java.io.Serializable;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListManga extends AppCompatActivity implements Serializable{
    RetrofitService retrofitService = new RetrofitService();
    private List<Product> comicList;
    private RecyclerView recycler_comic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manga);
        recycler_comic = findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recycler_comic.setLayoutManager(gridLayoutManager);
        fetchComic();

    }

    private void fetchComic() {
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getComicList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                comicList = response.body();
                AdapterProduct myComicAdapter = new AdapterProduct(comicList, new IClickItemProductListener() {
                    @Override
                    public void onClickItemUser(Product product) {
                        onClickGoToChaperList(product);
                    }
                });
                recycler_comic.setAdapter(myComicAdapter);

            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.v("TAG", "eeeeeeeeeeee=" + t);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void onClickGoToChaperList(Product product){
        Intent intent = new Intent(ListManga.this, ListChapter.class);
        startActivity(intent);
    }

}