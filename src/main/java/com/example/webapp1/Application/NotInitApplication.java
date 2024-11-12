package com.example.webapp1.Application;

import com.example.webapp1.Data.UserData;
import com.example.webapp1.Users.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NotInitApplication implements IApplication
{
    private User findUserById(UserData userData, int userId) {
        for (User user : userData.GetAll()) {
            if (user.Id == userId) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/notinitapp")
    public String Show(Model model, HttpSession session) {
        UserData _userData = (UserData) session.getAttribute("_userData");
        if (_userData == null) {
            _userData = new UserData();
            session.setAttribute("_userData", _userData);
        }

        List<User> users = _userData.GetAll();
        model.addAttribute("users", users);
        return "LogOrReg";
    }

    @GetMapping("notinit/user/{id}")
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
