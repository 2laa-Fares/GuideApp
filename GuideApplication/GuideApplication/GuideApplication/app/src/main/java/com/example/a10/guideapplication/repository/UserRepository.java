package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.UserApi;

public interface UserRepository {
    void getOwners(OnRequestCompleteListenerUser listener);
    void login(String data, OnRequestCompleteListenerUser listener);
    void register(UserApi user, OnRequestCompleteListenerUser listener);
    void editProfile(UserApi user, OnRequestCompleteListenerUser listener);
    void deleteUser(int userID, boolean deleteSection, OnRequestCompleteListenerUser listener);
}
