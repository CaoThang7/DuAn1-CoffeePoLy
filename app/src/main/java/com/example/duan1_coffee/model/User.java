package com.example.duan1_coffee.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId , username, fullName, email, image, phone, password;

    public User() {
    }


    public User(String userId , String username, String fullName, String email, String phone, String password) {
        this.userId=userId;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.image = username.substring(0,1).toUpperCase();
        this.phone = phone;
        this.password = password;
    }


    public User(String username, String email,String phone) {
        this.username = username;
        this.email = email;
        this.fullName = username;
        this.image = username.substring(0,1).toUpperCase();
        this.phone = phone;
    }
    public User(String username,String phone) {
        this.username = username;
        this.phone = phone;

    }

    public User(String password) {
        this.password = password;
    }

    public User(String userId, String username, String email, String image) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    //Update 2 value username,phone
    @Exclude
    public Map<String, Object>  userUpdate() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("phone", phone);
        return result;
    }

    //Update value password
    @Exclude
    public Map<String, Object>  userUpdate2() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("password", password);
        return result;
    }
}
