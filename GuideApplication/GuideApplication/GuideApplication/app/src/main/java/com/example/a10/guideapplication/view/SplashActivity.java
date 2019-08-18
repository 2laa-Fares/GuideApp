package com.example.a10.guideapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.TokenPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.TokenRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements TokenListener{
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.addUserButton)
    Button addUserButton;

    TokenListener listener;
    TokenPresenter presenter;

    User user;
    Token token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        App.setContext(this);

        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
        Gson gson = new Gson();
        user = gson.fromJson(isLogin, User.class);

        if(isLogin != null){
            String tokenString = PrefUtils.getKeys(this, getString(R.string.token));
            token = gson.fromJson(tokenString, Token.class);

            Date tokenDate = null;
            if(token != null) tokenDate = token.getExpireDate();
            Date date = Calendar.getInstance().getTime();

            if (tokenString == null || tokenString.equals("") || (tokenDate != null && tokenDate.before(date))){
                listener = this;
                presenter = new TokenPresenter(listener, new TokenRepositoryImp());

                Map<String, String> body = new HashMap<>();
                body.put("UserName", user.getUserName());
                body.put("Password", user.getPassword());
                body.put("grant_type", "password");
                presenter.getToken(user.getUserName(), user.getPassword(), "password");
            }else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            loginButton.setVisibility(View.VISIBLE);
            addUserButton.setVisibility(View.VISIBLE);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            });

            addUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void token(Token token) {
        if (token!=null){
            Gson gson = new Gson();
            String json = gson.toJson(token);
            PrefUtils.storeKeys(App.getContext(), getString(R.string.token), json);

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(App.getContext(), "انت لا تمتلك صلاحية للوصول الى البيانات", Toast.LENGTH_LONG).show();
        }
    }
}
