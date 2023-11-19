package com.example.duan1bookapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemProductListener;
import com.example.duan1bookapp.activities.ListChapter;
import com.example.duan1bookapp.adapters.MyComicAdapter;
import com.example.duan1bookapp.adapters.SliderAdapterExample;
import com.example.duan1bookapp.databinding.FragmentHomeBinding;
import com.example.duan1bookapp.models.Product;
import com.example.duan1bookapp.models.slideShow;
import com.example.duan1bookapp.retrofit.IComicAPI;
import com.example.duan1bookapp.retrofit.RetrofitService;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RetrofitService retrofitService = new RetrofitService();
    private List<Product> comicList;
    private RecyclerView recycler_comic;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    List<slideShow> slideShowsList;
    SliderAdapterExample sliderAdapterExample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        loadSlideShow();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_comic = view.findViewById(R.id.recycler_comic_1);
        recycler_comic.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fetchComic();
    }

    private void fetchComic() {
        IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getComicList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                comicList = response.body();
                MyComicAdapter myComicAdapter = new MyComicAdapter(comicList, new IClickItemProductListener() {
                    @Override
                    public void onClickItemUser(Product product) {
                        onClickGoToChapterList(product);
                    }
                });
                recycler_comic.setAdapter(myComicAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.v("TAG", "Error: " + t);
            }
        });
    }

    private void onClickGoToChapterList(Product product) {
        Intent intent = new Intent(getContext(), ListChapter.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    // Cài đặt phương thức để tải slideshow
    private void loadSlideShow() {
        slideShowsList = new ArrayList<>();
        IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
        Call<List<slideShow>> call = iComicAPI.getShowData();

        call.enqueue(new Callback<List<slideShow>>() {
            @Override
            public void onResponse(Call<List<slideShow>> call, Response<List<slideShow>> response) {
                if (response.isSuccessful()) {
                    slideShowsList = (ArrayList<slideShow>) response.body();

                    if (slideShowsList.size() > 5) {
                        slideShowsList = slideShowsList.subList(0, 5);
                    }

                    sliderAdapterExample = new SliderAdapterExample(getContext(), slideShowsList);
                    binding.imageSlider.setSliderAdapter(sliderAdapterExample);
                    binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
                    binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
                    binding.imageSlider.setScrollTimeInSec(4);
                    binding.imageSlider.startAutoCycle();

                }
            }

            @Override
            public void onFailure(Call<List<slideShow>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

}
