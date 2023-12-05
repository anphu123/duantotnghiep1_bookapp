package com.example.duan1bookapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemProductListener;
import com.example.duan1bookapp.models.slideShow;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.MyViewHolder1> {
    private List<slideShow> mSliderItems;
    private IClickItemProductListener iClickItemProductListener;
    private Context context;



    public SliderAdapterExample(Context context, List<slideShow> sliderItems) {
        this.context = context;
        this.mSliderItems = sliderItems;
        this.iClickItemProductListener = iClickItemProductListener;

    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_slide_show, null);
        return new MyViewHolder1(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, int position) {
        slideShow product = mSliderItems.get(position);
        if (product == null) {
            return;
        }
        String url = Constants.URL_API+ "/api/v1/product/image/" + product.productImageName; // Sửa productImageName thành productImageNameS
        Glide.with(holder.imageView.getContext()).load(url).into(holder.imageView);
        holder.textView.setText(product.productName);

        // Đảm bảo rằng bạn đã khai báo iClickItemProductListener và sử dụng nó khi người dùng nhấn vào một mục.
        
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    class MyViewHolder1 extends SliderViewAdapter.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder1(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSlide);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
