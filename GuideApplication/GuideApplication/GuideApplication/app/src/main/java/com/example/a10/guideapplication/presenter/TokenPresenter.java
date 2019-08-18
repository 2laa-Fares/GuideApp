package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.repository.TokenRepository;
import com.example.a10.guideapplication.view.TokenListener;

import java.util.Map;

public class TokenPresenter {
    TokenListener listener;
    TokenRepository repository;

    public TokenPresenter(TokenListener listener, TokenRepository repository){
        this.listener = listener;
        this.repository = repository;
    }

    public void getToken(String userName, String password, String grantType){
        TokenListener tokenListener = new TokenListener() {
            @Override
            public void token(Token token) {
                listener.token(token);
            }
        };
        repository.getToken(userName, password, grantType, tokenListener);
    }
}
