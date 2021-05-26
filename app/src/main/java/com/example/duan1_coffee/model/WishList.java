package com.example.duan1_coffee.model;

public class WishList {
    private String id;
    private String email;
    private String productId;
    private String productName;
    private String productImg;
    private float total;
    private boolean isWish;

    public WishList() {
    }

    public WishList(String id, String email, String productId, String productName, String productImg, float total, boolean isWish) {
        this.id = id;
        this.email = email;
        this.productId = productId;
        this.productName = productName;
        this.productImg = productImg;
        this.total = total;
        this.isWish = isWish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public boolean isWish() {
        return isWish;
    }

    public void setWish(boolean wish) {
        isWish = wish;
    }

    @Override
    public String toString() {
        return "WishList{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImg='" + productImg + '\'' +
                ", total='" + total + '\'' +
                ", isWish=" + isWish +
                '}';
    }
}
