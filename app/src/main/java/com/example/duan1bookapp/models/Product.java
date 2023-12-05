package com.example.duan1bookapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Product implements Serializable {
    public int id;
    public String productName;
    public String employeeName;
    public String productAuthor;
    public String productShortDescription;
    public String productImageName;
    public int soTap;
    public Date updatedAt;
    public ArrayList<Object> chapter;
    public ArrayList<Category> categories;

    public int price;
    public int view;
    public Product(int id, String productName, String productImageName) {
        this.id = id;
        this.productName = productName;
        this.productImageName = productImageName;
    }

    public Product() {
    }

    public Product(int id, String productName, String employeeName, String productAuthor, String productShortDescription, String productImageName, int soTap, Date updatedAt, ArrayList<Object> chapter, ArrayList<Category> categories) {
        this.id = id;
        this.productName = productName;
        this.employeeName = employeeName;
        this.productAuthor = productAuthor;
        this.productShortDescription = productShortDescription;
        this.productImageName = productImageName;
        this.soTap = soTap;
        this.updatedAt = updatedAt;
        this.chapter = chapter;
        this.categories = categories;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProductAuthor() {
        return productAuthor;
    }

    public void setProductAuthor(String productAuthor) {
        this.productAuthor = productAuthor;
    }

    public String getProductShortDescription() {
        return productShortDescription;
    }

    public void setProductShortDescription(String productShortDescription) {
        this.productShortDescription = productShortDescription;
    }

    public String getProductImageName() {
        return productImageName;
    }

    public void setProductImageName(String productImageName) {
        this.productImageName = productImageName;
    }

    public int getSoTap() {
        return soTap;
    }

    public void setSoTap(int soTap) {
        this.soTap = soTap;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ArrayList<Object> getChapter() {
        return chapter;
    }

    public void setChapter(ArrayList<Object> chapter) {
        this.chapter = chapter;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public Product(int id, String productName, String employeeName, String productAuthor, String productShortDescription, String productImageName, int soTap, Date updatedAt, ArrayList<Object> chapter, ArrayList<Category> categories, int price, int view) {
        this.id = id;
        this.productName = productName;
        this.employeeName = employeeName;
        this.productAuthor = productAuthor;
        this.productShortDescription = productShortDescription;
        this.productImageName = productImageName;
        this.soTap = soTap;
        this.updatedAt = updatedAt;
        this.chapter = chapter;
        this.categories = categories;
        this.price = price;
        this.view = view;
    }
}
