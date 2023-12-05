package com.example.duan1bookapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class CategoryFragment extends Fragment {

     RetrofitService retrofitService = new RetrofitService();
    private List<Product> comicList;
    private RecyclerView recycler_comic;
    private ProgressBar mProgressBar;
    private Button btn1,btn2,btn3;
    private View  view;
    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        btn1 = view.findViewById(R.id.type_btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchComic("commic");
            }
        });
        
        mProgressBar = view.findViewById(R.id.progress_mangass);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_comic = view.findViewById(R.id.recycler_comic_1);
        recycler_comic.setLayoutManager(new GridLayoutManager(getContext(),2));
        fetchComic("comic2");
    }
    private void fetchComic(String type) {
        mProgressBar.setVisibility(View.VISIBLE);
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getComicByTypeList(type).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
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
                recycler_comic.setAdapter(myComicAdapter);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.v("TAG", "eeeeeeeeeeee=" + t);
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
    private void onClickGoToChaperList(Product product){
        Intent intent = new Intent(getContext(), ListChapter.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product",product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}