package com.example.duan1bookapp.models;

import java.io.Serializable;

public class slideShow implements Serializable {
    public int id;
    public String productName;
    public String productImageName;

    public slideShow(int id, String productName, String productImageName) {
        this.id = id;
        this.productName = productName;
        this.productImageName = productImageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageName() {
        return productImageName;
    }

    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }
}
