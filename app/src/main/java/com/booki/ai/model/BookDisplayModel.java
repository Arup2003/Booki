package com.booki.ai.model;

public class BookDisplayModel {

    private String name;
    private String author;
    private String streakRequirement;
    private float price;
    private String summary;
    private int pages;
    private String language;
    private String downloads;
    private String aboutAuthor;

    public BookDisplayModel(String name, String author, String streakRequirement, float price, String summary, int pages, String language, String downloads, String aboutAuthor) {
        this.name = name;
        this.author = author;
        this.streakRequirement = streakRequirement;
        this.price = price;
        this.summary = summary;
        this.pages = pages;
        this.language = language;
        this.downloads = downloads;
        this.aboutAuthor = aboutAuthor;
    }

    public BookDisplayModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStreakRequirement() {
        return streakRequirement;
    }

    public void setStreakRequirement(String streakRequirement) {
        this.streakRequirement = streakRequirement;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public String getAboutAuthor() {
        return aboutAuthor;
    }

    public void setAboutAuthor(String aboutAuthor) {
        this.aboutAuthor = aboutAuthor;
    }
}
