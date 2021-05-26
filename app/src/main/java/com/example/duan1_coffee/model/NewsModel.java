package com.example.duan1_coffee.model;

public class NewsModel {
    public String titleTinTucVne;
    public String linkTinTucVne;
    public String hinhanhTinTucVne;
    public String dateTinTucVne;

    public NewsModel() {
    }

    public NewsModel(String titleTinTucVne, String linkTinTucVne, String hinhanhTinTucVne, String dateTinTucVne) {
        this.titleTinTucVne = titleTinTucVne;
        this.linkTinTucVne = linkTinTucVne;
        this.hinhanhTinTucVne = hinhanhTinTucVne;
        this.dateTinTucVne = dateTinTucVne;
    }

    public String getTitleTinTucVne() {
        return titleTinTucVne;
    }

    public void setTitleTinTucVne(String titleTinTucVne) {
        this.titleTinTucVne = titleTinTucVne;
    }

    public String getLinkTinTucVne() {
        return linkTinTucVne;
    }

    public void setLinkTinTucVne(String linkTinTucVne) {
        this.linkTinTucVne = linkTinTucVne;
    }

    public String getHinhanhTinTucVne() {
        return hinhanhTinTucVne;
    }

    public void setHinhanhTinTucVne(String hinhanhTinTucVne) {
        this.hinhanhTinTucVne = hinhanhTinTucVne;
    }

    public String getDateTinTucVne() {
        return dateTinTucVne;
    }

    public void setDateTinTucVne(String dateTinTucVne) {
        this.dateTinTucVne = dateTinTucVne;
    }
}
