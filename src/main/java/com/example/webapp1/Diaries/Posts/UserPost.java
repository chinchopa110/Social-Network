package com.example.webapp1.Diaries.Posts;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserPost implements IPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String title;
    private final String date;
    private final String message;

    public UserPost() {
        title = null;
        date = null;
        message = null;
    }

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
