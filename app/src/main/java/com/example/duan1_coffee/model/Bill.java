package com.example.duan1_coffee.model;

import java.util.Date;

public class Bill {
    private String id;
    private String username;
    private String date;
    private boolean state;

    public Bill() {
    }

    public Bill(String id, String username, String date, boolean state) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", state=" + state +
                '}';
    }
}
