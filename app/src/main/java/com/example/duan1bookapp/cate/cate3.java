package com.example.duan1bookapp.cate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemProductListener;
import com.example.duan1bookapp.activities.ListChapter;
import com.example.duan1bookapp.adapters.AdapterProduct;
import com.example.duan1bookapp.models.Product;
import com.example.duan1bookapp.retrofit.IComicAPI;
import com.example.duan1bookapp.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cate3 extends AppCompatActivity {

    RetrofitService retrofitService = new RetrofitService();

    private RecyclerView recyclerComic;
    private ProgressBar mProgressBar;
    private List<Product> comicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate3);
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại trang trước
            }
        });
        recyclerComic = findViewById(R.id.recycler_comic_1); // Replace with your RecyclerView ID
        mProgressBar = findViewById(R.id.progress_mangass); // Replace with your ProgressBar ID

        recyclerComic.setLayoutManager(new GridLayoutManager(this, 2));
        fetchComic("Comedy");
    }
    private void fetchComic(String type) {
        mProgressBar.setVisibility(View.VISIBLE);
        IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getComicByTypeList(type).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                mProgressBar.setVisibility(View.GONE);
                comicList = response.body();
                AdapterProduct myComicAdapter = new AdapterProduct(comicList, new IClickItemProductListener() {
                    @Override
                    public void onClickItemUser(Product product) {
                        onClickGoToChaperList(product);
                    }
                });
                recyclerComic.setAdapter(myComicAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.v("TAG", "eeeeeeeeeeee=" + t);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void onClickGoToChaperList(Product product) {
        Intent intent = new Intent(cate3.this, ListChapter.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("object_product", product);
        bundle.putSerializable("object_Category", product.categories);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}