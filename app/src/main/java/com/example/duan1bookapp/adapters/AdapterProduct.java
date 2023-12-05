package com.example.duan1bookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemProductListener;
import com.example.duan1bookapp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder> {

    private List<Product> originalList;
    private List<Product> filteredList;
    private IClickItemProductListener iClickItemProductListener;

    public AdapterProduct(List<Product> comicList, IClickItemProductListener iClickItemProductListener) {
        this.originalList = new ArrayList<>(comicList);
        this.filteredList = new ArrayList<>(comicList);
        this.iClickItemProductListener = iClickItemProductListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = filteredList.get(position);
        if (product == null) {
            return;
        }
        String url = Constants.URL_API + "/api/v1/product/image/" + product.productImageName;
        Glide.with(holder.imageView.getContext()).load(url).into(holder.imageView);
        holder.textView.setText(product.productName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickItemUser(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Product product : originalList) {
                if (product.productName.toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(product);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.manga_name);
        }
    }
}
