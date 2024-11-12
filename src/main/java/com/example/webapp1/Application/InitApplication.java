package com.example.webapp1.Application;

import com.example.webapp1.Diaries.Posts.IPost;
import com.example.webapp1.Diaries.Posts.UserPost;
import com.example.webapp1.Users.MyProfile;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.webapp1.Data.UserData;
import com.example.webapp1.Users.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class InitApplication implements IApplication {

    private User findUserById(UserData userData, int userId) {
        for (User user : userData.GetAll()) {
            if (user.Id == userId) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/initapp")
    public String Show(Model model, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");
        UserData userData = (UserData) session.getAttribute("_userData");

        List<User> users = userData.GetAll();
        model.addAttribute("users", users);
        model.addAttribute("myProfile", myProfile);
        return "homePage";
    }

    @PostMapping("/addPost")
    public String addPost(@RequestParam String title, @RequestParam String message, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");
        UserData _userData = (UserData) session.getAttribute("_userData");

        if (myProfile != null) {
            String date = LocalDate.now().toString();
            IPost newPost = new UserPost(title, date, message);
            myProfile.Diary.addPost(newPost);

            User user = findUserById(_userData, myProfile.Id);
            if (user != null) {
                user.Diary.addPost(newPost);
                _userData.Update(user);
            }

            return "redirect:/yourDiary";
        } else {
            return "redirect:/loginForm";
        }
    }

    @PostMapping("/removePost")
    public String removePost(@RequestParam int postIndex, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");
        UserData _userData = (UserData) session.getAttribute("_userData");

        if (myProfile != null) {
            User user = findUserById(_userData, myProfile.Id);
            if (user != null) {
                if (postIndex >= 0 && postIndex < myProfile.Diary.getPosts().size() && postIndex < user.Diary.getPosts().size()) {
                    myProfile.Diary.removePost(postIndex);
                    user.Diary.removePost(postIndex);
                    _userData.Update(user);
                }
                return "redirect:/yourDiary";
            }
        }
        return "redirect:/loginForm";
    }

    @GetMapping("/yourDiary")
    public String ShowMyDiary(Model model, HttpSession session) {
        MyProfile myProfile = (MyProfile) session.getAttribute("myProfile");

        if (myProfile != null) {
            model.addAttribute("diary", myProfile.Diary.getPosts());
            model.addAttribute("userName", myProfile.Name);
            return "yourDiary";
        } else {
            model.addAttribute("error", "Пожалуйста, войдите в систему.");
            return "loginForm";
        }
    }

    @GetMapping("init/user/{id}")
    public String ShowUserDiary(@PathVariable int id, Model model, HttpSession session) {
        UserData userData = (UserData) session.getAttribute("_userData");
        User user = findUserById(userData, id);

        if (user != null) {
            model.addAttribute("diary", user.Diary.getPosts());
            model.addAttribute("userName", user.Name);
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }
        return "userDiary";
    }
}
