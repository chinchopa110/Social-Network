package com.example.webapp1.Users;

import com.example.webapp1.Diaries.UserDiary;

public class User {
    private final String _passwordHash;

    public String Name;
    public UserDiary Diary;
    public int Id;

    private User(Builder builder) {
        this.Name = builder.name;
        this._passwordHash = builder.passwordHash;
        this.Diary = builder.diary;
        this.Id = builder.id;
    }

    public boolean Login(String input){
        return input.equals(_passwordHash);
    }

    public static class Builder {
        private int id;
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
            for(var post : userDiary.getPosts()) {
                diary.addPost(post);
            }
            return this;
        }

        public User.Builder setId(int id) {
            this.id = id;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
