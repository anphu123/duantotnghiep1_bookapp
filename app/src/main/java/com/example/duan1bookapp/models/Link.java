package com.example.duan1bookapp.models;

import java.io.Serializable;

public class Link implements Serializable {
    public int id;
    public int linkid;
    public String inamgeName;

    public Link() {
    }

    public Link(int id, int linkid, String inamgeName) {
        this.id = id;
        this.linkid = linkid;
        this.inamgeName = inamgeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLinkid() {
        return linkid;
    }

    public void setLinkid(int linkid) {
        this.linkid = linkid;
    }

    public String getInamgeName() {
        return inamgeName;
    }

    public void setInamgeName(String inamgeName) {
        this.inamgeName = inamgeName;
    }
}
