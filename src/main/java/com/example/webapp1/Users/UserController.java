package com.example.webapp1.Users;

import com.example.webapp1.Data.UserData;

import com.example.webapp1.Users.Service.Generators.IdGenerator;
import com.example.webapp1.Users.Service.PasswordHandler.HashPassword;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final IdGenerator _idGenerator;

    public UserController(IdGenerator idGenerator) {
        _idGenerator = idGenerator;
    }

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
        UserData _userData = (UserData) session.getAttribute("_userData");
        for (User user : _userData.GetAll()) {
            if (user.Name.equals(name) && user.Login(HashPassword.Hash(password))) {

                MyProfile myProfile = new MyProfile.Builder()
                        .setName(name)
                        .setPassword(HashPassword.Hash(password))
                        .setId(user.Id)
                        .setDiary(user.Diary)
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
        UserData _userData = (UserData) session.getAttribute("_userData");
        if (_userData == null) {
            _userData = new UserData();
            session.setAttribute("_userData", _userData);
        }
        int id = _idGenerator.Generate();

        User user = new User.Builder()
                .setName(name)
                .setPassword(HashPassword.Hash(password))
                .setId(id)
                .build();
        _userData.Add(user);

        MyProfile myProfile = new MyProfile.Builder()
                .setName(name)
                .setPassword(HashPassword.Hash(password))
                .setId(id)
                .build();

        session.setAttribute("myProfile", myProfile);
        session.setAttribute("_userData", _userData);

        model.addAttribute("message", "Вы зарегистрированы!");
        return "welcome";
    }
}
