package com.five5.groceryapp;

public class item {
    String name;
    String rate;
    String info;

    public item(String name, String rate, String info) {
        this.name = name;
        this.rate = rate;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
