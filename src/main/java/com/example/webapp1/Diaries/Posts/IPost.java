package com.example.webapp1.Diaries.Posts;

import com.example.webapp1.Users.Domain.User;

public interface IPost {
    String getDate();
    String getTime();
    String getMessage();
    int getId();

    boolean hasLiked(User user);
    void likePost(User user);
    void unlikePost(User user);
    int getLikesCount();
}

