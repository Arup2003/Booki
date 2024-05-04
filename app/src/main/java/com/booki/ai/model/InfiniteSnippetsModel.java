package com.booki.ai.model;

public class InfiniteSnippetsModel {

    private String _id;
    private String heading;
    private String body;
    private String imgSrc;

    public InfiniteSnippetsModel(String _id, String heading, String body, String imgSrc) {
        this._id = _id;
        this.heading = heading;
        this.body = body;
        this.imgSrc = imgSrc;
    }

    public InfiniteSnippetsModel() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
