package com.example.duan1_coffee.model;

import java.util.List;

public class ProductType {
    private String typeId;
    private String typeName;
    private String imageType;

    List<Product> productList;

    public ProductType() {
    }

    public ProductType(String typeName, List<Product> productList) {
        this.typeName = typeName;
        this.productList = productList;
    }

    public ProductType(String typeName, String imageType) {
        this.typeName = typeName;
        this.imageType = imageType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
