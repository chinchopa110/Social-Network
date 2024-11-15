package com.example.webapp1.Diaries.Posts;


import com.example.webapp1.Diaries.Posts.Response.UserComment;
import com.example.webapp1.Diaries.Posts.Response.UserLike;
import com.example.webapp1.Diaries.Posts.UserPostService.UserPostComparator;
import com.example.webapp1.Users.Domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserPost implements IPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String title;
    private final String date;
    private final String time;
    private final String message;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<UserLike> likes;

    @Column(name = "likes_count", nullable = false)
    private int likesCount;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<UserComment> comments;

    @Column(name = "comment_count", nullable = false)
    private int commentsCount;

    public UserPost() {
        this.title = null;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = null;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.likesCount = 0;
        this.commentsCount = 0;
    }

    public UserPost(String title, String message) {
        this.title = title;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = message;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.likesCount = 0;
        this.commentsCount = 0;
    }

    public UserPost(String title, String message, List<UserLike> likes, List<UserComment> comments) {
        this.title = title;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = message;
        this.likes = likes;
        this.comments = comments;
        this.likesCount = likes.size();
        this.commentsCount = comments.size();
    }

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

    @Override
    public String getTime(){
        return time;
    }

    @Override
    public int getLikesCount() {
        return likesCount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean hasLiked(User user) {
        for (UserLike userLike : likes) {
            if (userLike != null && userLike.getUser() != null && userLike.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void likePost(User user) {
        if (user != null) {
            likes.add(new UserLike(user));
            likesCount = likes.size();
        }
    }

    @Override
    public void unlikePost(User user) {
        if (user != null) {
            likes.removeIf(userLike -> userLike.getUser().equals(user));
            likesCount = likes.size();
        }
    }

    public void addComment(User user, String comment) {
        comments.add(new UserComment(user, comment));
    }

    public List<UserComment> getComments() {
        List<UserComment> sortedComments = comments;

        sortedComments.sort(new UserPostComparator());

        return sortedComments;
    }
}
