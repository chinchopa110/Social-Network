package com.example.webapp1.Data;

import com.example.webapp1.Users.User;
import java.util.ArrayList;

public class UserData implements IData<User> {
    private final ArrayList<User> users = new ArrayList<>();

    @Override
    public void Add(User user) {
        users.add(user);
    }

    @Override
    public void Remove(User user) {
        users.remove(user);
    }

    @Override
    public ArrayList<User> GetAll() {
        return users;
    }

    @Override
    public boolean Update(User updatedUser) {

        for (int i = 0; i < users.size(); i++) {
            User existingUser = users.get(i);
            if (existingUser.Id == updatedUser.Id) {
                users.set(i, updatedUser);
                return true;
            }
        }
        return false;
    }
}
