package com.example.restaurantes.model;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private long id;
    private String name;
    private String description;
    private String type;

    public Restaurant() {
    }

    public Restaurant(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
