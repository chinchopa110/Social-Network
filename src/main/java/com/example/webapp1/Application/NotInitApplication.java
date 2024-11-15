package com.example.webapp1.Application;

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
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }
        return "userDiaryNotInit";
    }

    //TODO комменты
}
