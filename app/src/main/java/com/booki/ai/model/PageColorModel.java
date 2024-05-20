package com.booki.ai.model;

public class PageColorModel {

    int pageColor;
    int textColor;

    public PageColorModel(int pageColor, int textColor) {
        this.pageColor = pageColor;
        this.textColor = textColor;
    }

    public int getPageColor() {
        return pageColor;
    }

    public void setPageColor(int pageColor) {
        this.pageColor = pageColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
