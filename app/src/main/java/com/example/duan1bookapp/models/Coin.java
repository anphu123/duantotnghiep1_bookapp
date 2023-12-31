package com.example.duan1bookapp.models;

import java.io.Serializable;

public class Coin implements Serializable {

    public int id;
    public int value;


    public Coin() {
    }

    public Coin(int id) {
        this.id = id;
    }

    public Coin(int id, int value) {
        this.id = id;
        this.value = value;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "value=" + value +
                '}';
    }
}
