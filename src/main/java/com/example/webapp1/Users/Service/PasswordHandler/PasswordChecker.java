package com.example.webapp1.Users.Service.PasswordHandler;

public class PasswordChecker {
    public static boolean Check(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasSpecialChar = password.matches(".*[!&?_.].*");
        boolean hasUpperCase = password.matches(".*[A-Z].*");

        return hasSpecialChar && hasUpperCase;
    }
}
