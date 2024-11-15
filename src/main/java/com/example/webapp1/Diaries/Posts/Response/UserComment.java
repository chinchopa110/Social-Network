package com.example.webapp1.Diaries.Posts.Response;

import com.example.webapp1.Diaries.Posts.IPost;
import com.example.webapp1.Users.Domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserComment implements IResponse, IPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String date;
    private final String time;
    private final String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<UserLike> likes;

    @Column(name = "comment_likes_count", nullable = false)
    private int likesCount;

    public UserComment() {
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = null;
        this.user = new User();
        this.likes = new ArrayList<>();
        this.likesCount = 0;
    }

    public UserComment(User _user,  String message) {
        this.user = _user;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = message;
        this.likes = new ArrayList<>();
        this.likesCount = 0;
    }

    public UserComment(User _user,  String message, ArrayList<UserLike> likes) {
        this.user = _user;
        this.date = LocalDate.now().toString();
        this.time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.message = message;
        this.likes = likes;
        this.likesCount = likes.size();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getDate(){
        return date;
    }
    @Override
    public String getTime(){
        return time;
    }
    @Override
    public String getMessage(){
        return message;
    }
    @Override
    public int getLikesCount(){
        return likesCount;
    }
    @Override
    public int getId() {
        return id;
    }

    public boolean hasLiked(User user) {
        for (UserLike userLike : likes) {
            if (userLike != null && userLike.getUser() != null && userLike.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void likePost(User user) {
        if (user != null) {
            likes.add(new UserLike(user));
            likesCount = likes.size();
        }
    }

    public void unlikePost(User user) {
        if (user != null) {
            likes.removeIf(userLike -> userLike.getUser().equals(user));
            likesCount = likes.size();
        }
    }
}
