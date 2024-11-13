package com.example.webapp1.Application;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface IApplication {
    String Show(Model model, HttpSession session);
    String ShowUserDiary(@PathVariable int id, Model model);
}
