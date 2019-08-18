package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.model.UserApi;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerUser;
import com.example.a10.guideapplication.repository.UserRepository;
import com.example.a10.guideapplication.view.UserInterface;

import java.util.List;

public class UserPresenter {
    UserInterface userInterface;
    UserRepository repository;

    public UserPresenter(UserInterface userInterface, UserRepository repository) {
        this.userInterface = userInterface;
        this.repository = repository;
    }

    public void getUser(String data) {
        OnRequestCompleteListenerUser listener = new OnRequestCompleteListenerUser() {
            @Override
            public void user(User user) {
                userInterface.user(user);
            }

            @Override
            public void users(List<User> users) {

            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.login(data, listener);
    }

    public void setUser(UserApi user) {
        OnRequestCompleteListenerUser listener = new OnRequestCompleteListenerUser() {
            @Override
            public void user(User user) {
                userInterface.user(user);
            }

            @Override
            public void users(List<User> users) {

            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.register(user, listener);
    }

    public void editUser(UserApi user) {
        OnRequestCompleteListenerUser listener = new OnRequestCompleteListenerUser() {
            @Override
            public void user(User user) {
            }

            @Override
            public void users(List<User> users) {

            }

            @Override
            public void message(Boolean response) {
                userInterface.message(response);
            }
        };
        repository.editProfile(user, listener);
    }

    public void getOwners() {
        OnRequestCompleteListenerUser listener = new OnRequestCompleteListenerUser() {
            @Override
            public void user(User user) {
            }

            @Override
            public void users(List<User> users) {
                userInterface.users(users);
            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.getOwners(listener);
    }

    public void deleteUser(int userID, boolean deleteSection) {
        OnRequestCompleteListenerUser listener = new OnRequestCompleteListenerUser() {
            @Override
            public void user(User user) {
            }

            @Override
            public void users(List<User> users) {
            }

            @Override
            public void message(Boolean response) {
                userInterface.message(response);
            }
        };
        repository.deleteUser(userID, deleteSection, listener);
    }
}
