package com.example.duan1bookapp.models;

import java.io.Serializable;
import java.util.Date;

public class Bag implements Serializable {


    private Integer idbag;
    private Integer idcustomer;
    private Integer idchapter;

    private String type;

    private Date created_at;

    public Integer getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(Integer idcustomer) {
        this.idcustomer = idcustomer;
    }

    public Integer getIdchapter() {
        return idchapter;
    }

    public void setIdchapter(Integer idchapter) {
        this.idchapter = idchapter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }


    public Bag(Integer idcustomer, Integer idchapter, String type, Date created_at) {
        this.idcustomer = idcustomer;
        this.idchapter = idchapter;
        this.type = type;
        this.created_at = created_at;
    }
}
