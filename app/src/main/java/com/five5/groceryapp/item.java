package com.five5.groceryapp;

public class item {
    String name;
    int rate;
    String info;
    String category;
    int id;

    public item(String name, int rate, String info, String category, int id) {
        this.name = name;
        this.rate = rate;
        this.info = info;
        this.category = category;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
