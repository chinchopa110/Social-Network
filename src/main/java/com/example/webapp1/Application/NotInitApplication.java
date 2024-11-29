package com.example.webapp1.Application;

import com.example.webapp1.Diaries.Posts.UserPost;
import com.example.webapp1.Diaries.UserDiary;
import com.example.webapp1.Repos.IUserData;
import com.example.webapp1.Users.Domain.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotInitApplication implements IApplication
{
    @Autowired
    private IUserData _userData;

    private User findUserById(int userId) {
        for (User user : _userData.findAll()) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/notinitapp")
    public String Show(Model model, HttpSession session) {
        Iterable<User> users = _userData.findAll();
        model.addAttribute("users", users);
        return "LogOrReg";
    }

    @GetMapping("notinit/user/{id}")
    public String ShowUserDiary(@PathVariable int id, Model model) {
        User user = findUserById(id);

        if (user != null) {
            model.addAttribute("diary", user.getDiary().getPosts());
            model.addAttribute("userName", user.getName());
            model.addAttribute("userId", user.getId());
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }
        return "userDiaryNotInit";
    }

    @GetMapping("notinit/user/{id}/comm/{postId}")
    public String showUserComment(@PathVariable int id, @PathVariable long postId, Model model) {
        User user = findUserById(id);

        if (user != null) {
            UserDiary userDiary = user.getDiary();
            UserPost post = userDiary.findPostById(postId);

            if (post != null) {
                model.addAttribute("comments", post.getComments());
                model.addAttribute("user", user);
            } else {
                model.addAttribute("error", "Пост не найден.");
            }
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }

        return "userCommentNotInit";
    }
}
