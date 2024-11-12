package com.example.webapp1.Diaries;

import com.example.webapp1.Diaries.Posts.IPost;

import java.util.List;

public interface IDiary {
    List<IPost> getPosts();
    void addPost(IPost post);
    void removePost(int ind);
}
