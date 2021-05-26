package com.example.duan1_coffee.model;

public class Cart {
    private String cartId;
    private String productName;
    private int productAmount;
    private String imgProduct;
    private float total;

    public Cart() {
    }

    public Cart(String productName, int productAmount, float total) {
        this.productName = productName;
        this.productAmount = productAmount;
        this.total = total;
    }

    public Cart(String productName, int productAmount, String imgProduct, float total) {
        this.productName = productName;
        this.productAmount = productAmount;
        this.imgProduct = imgProduct;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "productName='" + productName + '\'' +
                ", productAmount=" + productAmount +
                ", imgProduct='" + imgProduct + '\'' +
                ", total=" + total +
                '}';
    }
}
