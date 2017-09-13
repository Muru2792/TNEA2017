package com.in22labs.tneaapp.Adapter;


public class Notify {
    private String title;
    private String date;

    public Notify(String title, String date) {
        this.title = title;
        this.date = date;

    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public void setDate(String date){
        this.date = date;
    }


}