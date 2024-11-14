package com.example.webapp1.Diaries.Posts;


import com.example.webapp1.Response.UserLike;
import com.example.webapp1.Users.Domain.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserPost implements IPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String title;
    private final String date;
    private final String message;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<UserLike> likes;

    @Column(name = "likes_count", nullable = false)
    private int likesCount;

    public UserPost() {
        this.title = null;
        this.date = null;
        this.message = null;
        this.likes = new ArrayList<>();
        this.likesCount = 0;
    }

    public UserPost(String title, String date, String message) {
        this.title = title;
        this.date = date;
        this.message = message;
        this.likes = new ArrayList<>();
        this.likesCount = 0;
    }

    public UserPost(String title, String date, String message, List<UserLike> likes) {
        this.title = title;
        this.date = date;
        this.message = message;
        this.likes = likes;
        this.likesCount = likes.size();
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

    public int getLikesCount() {
        return likesCount;
    }

    public void likePost(User user) {
        for (UserLike userLike : likes) {
            if (userLike.getUser().equals(user)) {
                return;
            }
        }
        UserLike userLike = new UserLike(user);
        likes.add(userLike);
        likesCount = likes.size();
    }

    public void unlikePost(User user) {
        likes.removeIf(userLike -> userLike.getUser().equals(user));
        likesCount = likes.size();
    }

    public int getId() {
        return id;
    }
}
