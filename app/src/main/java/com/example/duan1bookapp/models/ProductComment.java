package com.example.duan1bookapp.models;


public class ProductComment {
    /*
    Modified sample data from: https://developer.github.com/v3/gists/comments/#list-comments-on-a-gist
    {
        "id": 1,
        "body": "Just commenting for the sake of commenting",
        "user": {

        },
        "created_at": "2011-04-18T23:23:56Z"
      }
    */
    public int id;
    public String body;
    public Customer user;
    public String created_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ProductComment(String body, Customer user, String created_at) {
        this.body = body;
        this.user = user;
        this.created_at = created_at;
    }

    public ProductComment(String body, String created_at) {
        this.body = body;
        this.created_at = created_at;
    }
}
