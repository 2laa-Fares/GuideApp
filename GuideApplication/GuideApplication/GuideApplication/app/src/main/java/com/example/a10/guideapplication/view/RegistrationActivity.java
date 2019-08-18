package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.model.UserApi;
import com.example.a10.guideapplication.presenter.TokenPresenter;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.TokenRepositoryImp;
import com.example.a10.guideapplication.repository.repositoryImp.UserRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.a10.guideapplication.utils.Utils.isOnline;

public class RegistrationActivity extends AppCompatActivity implements UserInterface, TokenListener {

    private static final String TAG = "TAG";
    @BindView(R.id.username)
    EditText userNameEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.email)
    EditText mailEditText;
    @BindView(R.id.phone)
    EditText phoneEditText;
    @BindView(R.id.location)
    EditText locationEditText;
    @BindView(R.id.job)
    EditText jobEditText;
    @BindView(R.id.addButton)
    Button addButton;
    @BindView(R.id.loginButton)
    Button loginButton;
    UserApi userApi = new UserApi();

    UserInterface userInterface;
    UserPresenter presenter;
    TokenListener listener;
    TokenPresenter tokenPresenter;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        App.setContext(this);

        userInterface = this;
        presenter = new UserPresenter(userInterface, new UserRepositoryImp());
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void register() {
        if (!validate()) {
            onRegisterFailed();
            return;
        }
        addButton.setEnabled(false);

        userApi.setUserName(userNameEditText.getText().toString());
        userApi.setPassword(passwordEditText.getText().toString());
        userApi.setMail(mailEditText.getText().toString());
        userApi.setPhone(Integer.parseInt(phoneEditText.getText().toString()));
        if(locationEditText.getText().toString() != null && !locationEditText.getText().toString().equals("")) userApi.setAddress(locationEditText.getText().toString());
        if(jobEditText.getText().toString() != null && !jobEditText.getText().toString().equals("")) userApi.setJob(jobEditText.getText().toString());
        userApi.setType(1);
        userApi.setCreateUser(1);

        if (isOnline(this)) {
            presenter.setUser(userApi);
            progressBar = new ProgressDialog(App.getContext());
            progressBar.setCancelable(true);
            progressBar.setMessage("يتم تسجيل المستخدم ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.show();
        } else {
            addButton.setEnabled(true);
            Toast.makeText(this, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    }

    private void onRegisterFailed() {
        addButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;
        String name = userNameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        String mail = mailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (name.isEmpty()) {
            userNameEditText.setError("برجاء ادخال اسم المستخدم");
            valid = false;
        } else {
            userNameEditText.setError(null);
        }

        if (pass.isEmpty()) {
            passwordEditText.setError("برجاء ادخال رقم المرور");
            valid = false;
        } else {
            if (pass.length() < 3) {
                passwordEditText.setError("رقم المرور يجب أن يكون أكثر من 3 حروف");
                valid = false;
            }
            passwordEditText.setError(null);
        }

        if (mail.isEmpty()) {
            mailEditText.setError("برجاء ادخال البريد الإلكتروني");
            valid = false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                mailEditText.setError("برجاء ادخال البريد الإلكتروني بشكل صحيح");
                valid = false;
            } else mailEditText.setError(null);
        }

        if (phone.isEmpty()) {
            phoneEditText.setError("برجاء ادخال رقم الهاتف");
            valid = false;
        } else {
            if (phone.length() != 9) {
                phoneEditText.setError("برجاء ادخال رقم الهاتف بشكل صحيح");
                valid = false;
            } else phoneEditText.setError(null);
        }

        return valid;
    }

    @Override
    public void user(User user) {
        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            PrefUtils.storeKeys(RegistrationActivity.this, getString(R.string.user), json);
            PrefUtils.storeKeys(App.getContext(), getString(R.string.userId), String.valueOf(user.getID()));

            listener = this;
            tokenPresenter = new TokenPresenter(listener, new TokenRepositoryImp());

            Map<String, String> body = new HashMap<>();
            body.put("UserName", user.getUserName());
            body.put("Password", user.getPassword());
            body.put("grant_type", "password");
            tokenPresenter.getToken(user.getUserName(), user.getPassword(), "password");
        } else {
            Toast.makeText(RegistrationActivity.this, "فشل تسجيل المستخدم برجاء ادخال البيانات الصحيحة", Toast.LENGTH_LONG).show();
            progressBar.dismiss();
            addButton.setEnabled(true);
        }
    }


    @Override
    public void message(Boolean response) {

    }

    @Override
    public void token(Token token) {
        if (token != null) {
            progressBar.dismiss();
            addButton.setEnabled(true);

            Gson gson = new Gson();
            String json = gson.toJson(token);
            PrefUtils.storeKeys(App.getContext(), getString(R.string.token), json);

            Toast.makeText(RegistrationActivity.this, "تم تسجيل بيانات المستخدم بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            progressBar.dismiss();
            addButton.setEnabled(true);

            Toast.makeText(App.getContext(), "برجاء المحاولة وقت أخر", Toast.LENGTH_LONG).show();
        }
    }
}
