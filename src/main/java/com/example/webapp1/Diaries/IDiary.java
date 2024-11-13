package com.example.webapp1.Diaries;

import com.example.webapp1.Diaries.Posts.IPost;
import com.example.webapp1.Diaries.Posts.UserPost;

import java.util.List;

public interface IDiary {
    List<UserPost> getPosts();
    void addPost(UserPost post);
    void removePost(int ind);
}
