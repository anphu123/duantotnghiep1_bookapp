package com.example.duan1bookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemChapterLinstener;
import com.example.duan1bookapp.models.Chapter;

import java.util.List;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.MyChapterViewHolder> {
    private List<Chapter> chapterList;
    private IClickItemChapterLinstener iClickItemChapterLinstener;

    public AdapterChapter(List<Chapter> chapterList, IClickItemChapterLinstener iClickItemChapterLinstener) {
        this.chapterList = chapterList;
        this.iClickItemChapterLinstener = iClickItemChapterLinstener;
    }

    @NonNull
    @Override
    public MyChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter,parent,false);
        return new MyChapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        if(chapter == null){
            return;
        }
        holder.textView1.setText(chapter.name);
        holder.textView2.setText(chapter.Date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemChapterLinstener.onClickItemChapter(chapter);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class MyChapterViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        public MyChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.Chapter_title);
            textView2 = (TextView) itemView.findViewById(R.id.Chapter_content);

        }
    }
}
