package com.booki.ai.model;

import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class InfiniteSnippetsModel {

    private String _id;
    private String heading;
    private String body;
    private StorageReference imgSrc;
    private ArrayList<String> tags;

    public InfiniteSnippetsModel(String _id, String heading, String body, StorageReference imgSrc, ArrayList<String> tags) {
        this._id = _id;
        this.heading = heading;
        this.body = body;
        this.imgSrc = imgSrc;
        this.tags = tags;
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

    public StorageReference getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(StorageReference imgSrc) {
        this.imgSrc = imgSrc;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
