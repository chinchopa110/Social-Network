package com.example.webapp1.Diaries.Posts.Response;

import com.example.webapp1.Users.Domain.User;
import jakarta.persistence.*;

@Entity
public class UserLike implements IResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User _user;

    public UserLike() {
        _user = new User();
    }

    public UserLike(User user) {
        this._user = user;
    }

    public User getUser() {
        return _user;
    }
}
