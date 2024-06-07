package com.booki.ai.model;

public class PageColorModel {

    int pageColor;
    String textColor;

    public PageColorModel(int pageColor, String textColor) {
        this.pageColor = pageColor;
        this.textColor = textColor;
    }

    public int getPageColor() {
        return pageColor;
    }

    public void setPageColor(int pageColor) {
        this.pageColor = pageColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
