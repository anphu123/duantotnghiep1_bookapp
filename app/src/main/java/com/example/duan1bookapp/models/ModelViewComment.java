package com.example.duan1bookapp.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.duan1bookapp.R;
import java.util.ArrayList;
import java.util.List;

public class ModelViewComment extends ViewModel {
    private MutableLiveData<List<ProductComment>> listMutableLiveData;
    private List<ProductComment> commentList;


    public ModelViewComment(){
        listMutableLiveData= new MutableLiveData<>();
        initData();
    }

    private void initData(){

        commentList = new ArrayList<>();
        commentList.add(new ProductComment("my first comment", new Customer("abc",R.mipmap.user_1),"2011-04-18 23:23:56"));
        commentList.add(new ProductComment("my first comment", new Customer("abc",R.mipmap.user_1),"2011-04-18 23:23:56"));
        commentList.add(new ProductComment("my first comment", new Customer("abc",R.mipmap.user_1),"2011-04-18 23:23:56"));
        listMutableLiveData.setValue(commentList);
    }

    public MutableLiveData<List<ProductComment>> getListMutableLiveData() {
        return listMutableLiveData;
    }
}
