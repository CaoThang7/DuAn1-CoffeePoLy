package com.example.duan1_coffee.model;

public class Product {
    public String productId;
    public String productName, productType, image;
    public float  price;
    public int amount;


    public Product() {
    }

    public Product(String productName, String productType, String image, float price) {
        this.productName = productName;
        this.productType = productType;
        this.image = image;
        this.price = price;
        this.amount = 1;
    }

    public Product(String productName, String productType, float price) {
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.amount = 1;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
