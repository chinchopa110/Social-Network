package com.example.webapp1.Diaries;

import com.example.webapp1.Diaries.Posts.UserPost;
import com.example.webapp1.Users.Domain.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserDiary implements IDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<UserPost> posts;

    public UserDiary() {
        this.posts = new ArrayList<>();
    }

    @Override
    public List<UserPost> getPosts() {
        return posts;
    }

    @Override
    public void addPost(UserPost post) {
        this.posts.add(post);
    }

    @Override
    public void removePost(int ind) {
        if (ind >= 0 && ind < posts.size()) {
            this.posts.remove(ind);
        }
    }

    public int getId() {
        return id;
    }

    public void likeDiaryPost(Long postId, User user) {
        UserPost post = findPostById(postId);
        if (post != null) {
            post.likePost(user);
        }
    }

    public void unlikeDiaryPost(Long postId, User user) {
        UserPost post = findPostById(postId);
        if (post != null) {
            post.unlikePost(user);
        }
    }

    private UserPost findPostById(Long postId) {
        for (UserPost post : posts) {
            if (post.getId() == postId) {
                return post;
            }
        }
        return null;
    }
}
