package com.example.webapp1.Diaries.Posts;


public class UserPost implements IPost {
    private String title;
    private String date;
    private String message;

    public UserPost(String title, String date, String message) {
        this.title = title;
        this.date = date;
        this.message = message;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
