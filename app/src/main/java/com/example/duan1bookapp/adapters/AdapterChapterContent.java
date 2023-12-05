package com.example.duan1bookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.models.Link;

import java.util.List;



public class AdapterChapterContent extends RecyclerView.Adapter<AdapterChapterContent.MyMangaViewHolder> {
    private List<Link> linkList;
    public AdapterChapterContent(List<Link> linkList) {
        this.linkList = linkList;
    }

    @NonNull
    @Override
    public MyMangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item,parent,false);
        return new AdapterChapterContent.MyMangaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMangaViewHolder holder, int position) {
        Link link = linkList.get(position);
        if(link == null){
            return;
        }
        String url = Constants.URL_API+ "/api/v1/product/image/"+link.inamgeName;
        Glide.with(holder.imageView.getContext()).load(url).timeout(6000).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public static class  MyMangaViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public MyMangaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.link_image_view);
        }
    }
}
