package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.User;

import java.util.List;

public interface OnRequestCompleteListenerUser {
    void user(User user);
    void users(List<User> users);
    void message(Boolean response);
}
