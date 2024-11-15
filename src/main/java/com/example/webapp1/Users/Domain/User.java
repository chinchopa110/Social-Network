package com.example.webapp1.Users.Domain;

import com.example.webapp1.Diaries.UserDiary;
import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String _passwordHash;

    private String Name;

    @OneToOne(cascade = CascadeType.ALL)
    private UserDiary Diary;

    public User() {
        this._passwordHash = "";
    }

    private User(Builder builder) {
        this.Name = builder.name;
        this._passwordHash = builder.passwordHash;
        this.Diary = builder.diary;
    }

    public UserDiary getDiary() { return Diary; }

    public String getPasswordHash() {
        return _passwordHash;
    }

    public String getName() {
        return Name;
    }

    public boolean Login(String input) {
        return input.equals(_passwordHash);
    }

    public static class Builder {
        private String name;
        private String passwordHash;
        private final UserDiary diary = new UserDiary();

        public User.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public User.Builder setPassword(String password) {
            this.passwordHash = password;
            return this;
        }

        public User.Builder setDiary(UserDiary userDiary) {
            for (var post : userDiary.getPosts()) {
                diary.addPost(post);
            }
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
    public int getId() {
        return id;
    }
}
