package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.User;

import java.util.List;

public interface UserInterface{
    void user(User user);
    void users(List<User> users);
    void message(Boolean response);
}
