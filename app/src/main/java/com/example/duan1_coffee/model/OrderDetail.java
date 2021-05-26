package com.example.duan1_coffee.model;

import java.util.Date;
import java.util.List;

public class OrderDetail {
    private String id;
    private String date;
    private boolean state;
    private String username;
    private String address;
    private String phone;
    private String email;
    private float total;
    private List<Cart> carts;

    public OrderDetail() {
    }

    public OrderDetail(String id, String date, boolean state, String username, String address, String phone, String email, float total, List<Cart> carts) {
        this.id = id;
        this.date = date;
        this.state = state;
        this.username = username;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.total = total;
        this.carts = carts;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", state=" + state +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", total=" + total +
                ", carts=" + carts +
                '}';
    }
}
