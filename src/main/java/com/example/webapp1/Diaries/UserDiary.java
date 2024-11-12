package com.example.webapp1.Diaries;

import com.example.webapp1.Diaries.Posts.IPost;

import java.util.ArrayList;
import java.util.List;

public class UserDiary implements IDiary {
    private final List<IPost> posts;

    public UserDiary() {
        this.posts = new ArrayList<>();
    }

    @Override
    public List<IPost> getPosts() {
        return posts;
    }

    @Override
    public void addPost(IPost post) {
        this.posts.add(post);
    }

    @Override
    public void removePost(int ind) {
        if (ind >= 0 && ind < posts.size()) {
            this.posts.remove(ind);
        }
    }
}
