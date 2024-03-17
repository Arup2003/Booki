package com.booki.ai;

public class InfiniteSnippetsModel {
    private String heading, body;
    private int image_src;

    public InfiniteSnippetsModel(String heading, String body, Integer image_src) {
        this.heading = heading;
        this.body = body;
        this.image_src = image_src;
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

    public Integer getImage_src() {
        return image_src;
    }

    public void setImage_src(Integer image_src) {
        this.image_src = image_src;
    }
}
