package com.example.projectedb;

public class CommentaryModel {
    private Integer id;
    private String title;
    private String body;

    public CommentaryModel() {}

    public CommentaryModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public CommentaryModel(Integer id, String title, String body) {
        this(title, body);
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
