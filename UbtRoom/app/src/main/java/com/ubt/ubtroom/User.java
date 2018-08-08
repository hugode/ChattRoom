package com.ubt.ubtroom;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String image;
    private String name;
    private String status;

    public User(String image, String name, String status, String id) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.id = id;
    }

    public User() {
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
