package com.example.webapp1.Users;

import com.example.webapp1.Users.Service.PasswordHandler.PasswordChecker;
import com.example.webapp1.Users.Domain.User;
import com.example.webapp1.Repos.IUserData;

import com.example.webapp1.Users.Service.PasswordHandler.HashPassword;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private IUserData _userData;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        User user = new User.Builder()
                .setName("")
                .setPassword("")
                .build();

        model.addAttribute("user", user);
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, Model model, HttpSession session) {
        for (User user : _userData.findAll()) {
            if (user.getName().equals(name) && user.Login(HashPassword.Hash(password))) {

                MyProfile myProfile = new MyProfile.Builder()
                        .setName(name)
                        .setPassword(HashPassword.Hash(password))
                        .setId(user.getId())
                        .setDiary(user.getDiary())
                        .build();

                session.setAttribute("myProfile", myProfile);

                model.addAttribute("message", "Добро пожаловать, " + name + "!");
                model.addAttribute("diary", myProfile.Diary.getPosts());
                return "yourDiary";
            }
        }
        model.addAttribute("error", "Неправильное имя пользователя или пароль!");
        return "loginForm";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User.Builder()
                .setName("")
                .setPassword("")
                .build();

        model.addAttribute("user", user);
        return "registrationForm";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String password, Model model, HttpSession session) {

        for (User user : _userData.findAll()) {
            if(user.getName().equals(name)){
                model.addAttribute("error", "Пользователь с таким именем уже сущесвтует.");
                return "registrationForm";
            }
        }

        if (!PasswordChecker.Check(password)) {
            model.addAttribute("error", "Пароль должен содержать как минимум 8 символов, одну заглавную букву и один специальный символ.");
            return "registrationForm";
        }

        User user = new User.Builder()
                .setName(name)
                .setPassword(HashPassword.Hash(password))
                .build();
        _userData.save(user);

        MyProfile myProfile = new MyProfile.Builder()
                .setName(name)
                .setPassword(HashPassword.Hash(password))
                .setId(user.getId())
                .build();

        session.setAttribute("myProfile", myProfile);

        model.addAttribute("message", "Вы зарегистрированы!");
        return "welcome";
    }
}
