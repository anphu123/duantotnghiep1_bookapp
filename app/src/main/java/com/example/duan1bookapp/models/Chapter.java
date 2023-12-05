package com.example.duan1bookapp.models;

import java.io.Serializable;

public class Chapter implements Serializable {

    public int id;
    public int mangaid;
    public String name;
    public int bag;
    public String date;
    public boolean paytoview;
    public Chapter() {
    }

    public Chapter(int id, int mangaid, String name) {
        this.id = id;
        this.mangaid = mangaid;
        this.name = name;
    }

    public Chapter(int id, int mangaid, String name, int bag) {
        this.id = id;
        this.mangaid = mangaid;
        this.name = name;
        this.bag = bag;
    }

    public Chapter(int mangaid, String name, int bag, boolean paytoview) {
        this.mangaid = mangaid;
        this.name = name;
        this.bag = bag;
        this.paytoview = paytoview;
    }

    public Chapter(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public Chapter(int id, int mangaid, String name, int bag, String date, boolean paytoview) {
        this.id = id;
        this.mangaid = mangaid;
        this.name = name;
        this.bag = bag;
        this.date = date;
        this.paytoview = paytoview;
    }
}
