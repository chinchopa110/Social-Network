package com.example.webapp1.Application;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

public interface IApplication {
    String Show(Model model, HttpSession session);
}
