package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.presenter.TokenPresenter;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.TokenRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.UserRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.a10.guideapplication.utils.Utils.isOnline;


public class LoginActivity extends AppCompatActivity implements UserInterface, TokenListener {

    @BindView(R.id.username)
    EditText userNameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.addUserButton)
    Button addUserButton;

    String userName, password, data;
    UserInterface userInterface;
    UserPresenter presenter;
    TokenListener listener;
    TokenPresenter tokenPresenter;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        App.setContext(this);

        userInterface = this;
        presenter = new UserPresenter(userInterface, new UserRepositoryImp());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(false);
        userName = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        data = userName + ":" + password;
        byte[] dataEncoded = data.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(dataEncoded, Base64.DEFAULT);

        if (isOnline(this)) {
            presenter.getUser(base64);
            progressBar = new ProgressDialog(App.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("يتم تسجيل الدخول ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
        } else {
            loginButton.setEnabled(true);
            Toast.makeText(this, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    }


    private void onLoginFailed() {
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String name = userNameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        if (name.isEmpty()) {
            userNameEditText.setError("برجاء ادخال اسم المستخدم");
            valid = false;
        } else {
            userNameEditText.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 3) {
            passwordEditText.setError("برجاء ادخال رقم المرور");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }
        return valid;
    }

    @Override
    public void user(User user) {
        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            PrefUtils.storeKeys(LoginActivity.this, getString(R.string.user), json);
            PrefUtils.storeKeys(App.getContext(), getString(R.string.userId), String.valueOf(user.getID()));

            listener = this;
            tokenPresenter = new TokenPresenter(listener, new TokenRepositoryImp());

            Map<String, String> body = new HashMap<>();
            body.put("UserName", user.getUserName());
            body.put("Password", user.getPassword());
            body.put("grant_type", "password");
            tokenPresenter.getToken(user.getUserName(), user.getPassword(), "password");

        } else {
            Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول برجاء ادخال البيانات الصحيحة", Toast.LENGTH_LONG).show();
            progressBar.dismiss();
            loginButton.setEnabled(true);
        }
    }

    @Override
    public void users(List<User> users) {

    }

    @Override
    public void message(Boolean response) {

    }

    @Override
    public void token(Token token) {
        if (token != null) {
            progressBar.dismiss();
            loginButton.setEnabled(true);

            Gson gson = new Gson();
            String json = gson.toJson(token);
            PrefUtils.storeKeys(App.getContext(), getString(R.string.token), json);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            progressBar.dismiss();
            loginButton.setEnabled(true);

            Toast.makeText(App.getContext(), "انت لا تمتلك صلاحية للوصول الى البيانات", Toast.LENGTH_LONG).show();
        }
    }
}
