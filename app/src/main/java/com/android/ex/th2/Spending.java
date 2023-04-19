package com.android.ex.th2;

import java.io.Serializable;

public class Spending implements Serializable {
    int id;
    String name;
    String description;
    double price;
    String date;

    public Spending() {
    }

    public Spending(int id, String name, String description, double price, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public Spending(String name, String description, double price, String date) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
