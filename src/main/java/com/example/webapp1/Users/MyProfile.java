package com.example.webapp1.Users;

import com.example.webapp1.Diaries.UserDiary;
import com.example.webapp1.Users.Service.PasswordHandler.HashPassword;

public class MyProfile
{
    private String _passwordHash;

    public String Name;
    public UserDiary Diary;
    public int Id;

    private MyProfile(Builder builder) {
        this.Name = builder.name;
        this._passwordHash = builder.passwordHash;
        this.Diary = builder.diary;
        this.Id = builder.id;
    }

    public boolean UpdatePassword(String newPassword) {
        String newHash = HashPassword.Hash(newPassword);
        if (newHash.equals(_passwordHash)) {
            _passwordHash = newHash;
            return true;
        }
        return false;
    }

    public static class Builder {
        private int id;
        private String name;
        private String passwordHash;
        private final UserDiary diary = new UserDiary();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPassword(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDiary(UserDiary userDiary) {
            for(var post : userDiary.getPosts()) {
                diary.addPost(post);
            }
            return this;
        }

        public MyProfile build() {
            return new MyProfile(this);
        }
    }
}
