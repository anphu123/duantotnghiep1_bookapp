package com.example.duan1bookapp.models;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private  int id;
    private boolean da_thanh_toan;
    private String noidung;
    private int customer_id;

    private Date created_at;
    public Order() {
    }

    public Order(int id, boolean da_thanh_toan, String noidung, int customer_id) {
        this.id = id;
        this.da_thanh_toan = da_thanh_toan;
        this.noidung = noidung;
        this.customer_id = customer_id;
    }

    public Order(boolean da_thanh_toan, String noidung, int customer_id) {
        this.da_thanh_toan = da_thanh_toan;
        this.noidung = noidung;
        this.customer_id = customer_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDa_thanh_toan() {
        return da_thanh_toan;
    }

    public void setDa_thanh_toan(boolean da_thanh_toan) {
        this.da_thanh_toan = da_thanh_toan;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
