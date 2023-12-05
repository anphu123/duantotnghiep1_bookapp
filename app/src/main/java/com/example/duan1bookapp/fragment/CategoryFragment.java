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
import com.example.duan1bookapp.cate.cate1;
import com.example.duan1bookapp.cate.cate2;
import com.example.duan1bookapp.cate.cate3;
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
    private Button btn1, btn2, btn3;
    private View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        btn1 = view.findViewById(R.id.type_btn1);
        btn2 = view.findViewById(R.id.type_btn2);
        btn3 = view.findViewById(R.id.type_btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), cate1.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), cate2.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), cate3.class));
            }
        });

        mProgressBar = view.findViewById(R.id.progress_mangass);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}

