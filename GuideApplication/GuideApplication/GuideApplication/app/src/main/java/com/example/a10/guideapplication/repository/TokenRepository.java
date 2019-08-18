package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.view.TokenListener;

import java.util.Map;

public interface TokenRepository {
    void getToken(String userName, String password, String grantType, TokenListener listener);
}
