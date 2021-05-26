package com.example.duan1_coffee.model;

import java.util.Date;

public class OrderState {
    private String id;
    private String date;
    private boolean state;
    private String email;

    public OrderState() {
    }

    public OrderState(String id, String date, boolean state, String email) {
        this.id = id;
        this.date = date;
        this.state = state;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "OrderState{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", state=" + state +
                ", username='" + email + '\'' +
                '}';
    }
}
