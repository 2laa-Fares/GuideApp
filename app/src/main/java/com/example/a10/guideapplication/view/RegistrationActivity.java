package com.example.a10.guideapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;
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
    @BindView(R.id.category)
    Spinner categorySpinner;

    UserApi userApi = new UserApi();

    UserInterface userInterface;
    UserPresenter presenter;
    TokenListener listener;
    TokenPresenter tokenPresenter;
    private ProgressDialog progressBar;

    User user;
    User editableUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        App.setContext(this);

        String isLogin = PrefUtils.getKeys(App.getContext(), getString(R.string.user));
        Gson gson = new Gson();
        user = gson.fromJson(isLogin, User.class);

        editableUser = (User) getIntent().getSerializableExtra("EditableUser");

        if(isLogin != null && user != null){
            loginButton.setVisibility(View.GONE);
            categorySpinner.setVisibility(View.VISIBLE);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.user_types_category_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);

            if(editableUser != null){
                addButton.setText("تعديل بيانات المستخدم");
                userNameEditText.setText(editableUser.getUserName());
                passwordEditText.setVisibility(View.GONE);
                mailEditText.setText(editableUser.getMail());
                if(editableUser.getPhone() != 0) phoneEditText.setText(String.valueOf(editableUser.getPhone()));
                locationEditText.setText(editableUser.getAddress());
                jobEditText.setText(editableUser.getJob());
                if(editableUser.getCategory().equals(getString(R.string.hotel))) categorySpinner.setSelection(3);
                else if(editableUser.getCategory().equals(getString(R.string.doctor))) categorySpinner.setSelection(1);
                else if(editableUser.getCategory().equals(getString(R.string.store))) categorySpinner.setSelection(2);
                else if(editableUser.getCategory().equals(getString(R.string.coffee))) categorySpinner.setSelection(4);
            }
        }

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

        if(editableUser != null){
            editableUser.setUserName(userNameEditText.getText().toString());
            editableUser.setMail(mailEditText.getText().toString());
            editableUser.setPhone(Integer.parseInt(phoneEditText.getText().toString()));
            if (locationEditText.getText().toString() != null && !locationEditText.getText().toString().equals(""))
                editableUser.setAddress(locationEditText.getText().toString());
            if (jobEditText.getText().toString() != null && !jobEditText.getText().toString().equals(""))
                editableUser.setJob(jobEditText.getText().toString());
            editableUser.setModifiedUser(user.getID());
            editableUser.setCategory(getCategory(categorySpinner.getSelectedItem().toString()));
            userApi = editableUser.getUserApi();
        }else {
            userApi.setUserName(userNameEditText.getText().toString());
            userApi.setPassword(passwordEditText.getText().toString());
            userApi.setMail(mailEditText.getText().toString());
            userApi.setPhone(Integer.parseInt(phoneEditText.getText().toString()));
            if (locationEditText.getText().toString() != null && !locationEditText.getText().toString().equals(""))
                userApi.setAddress(locationEditText.getText().toString());
            if (jobEditText.getText().toString() != null && !jobEditText.getText().toString().equals(""))
                userApi.setJob(jobEditText.getText().toString());


            if (user != null) {
                userApi.setType(2);
                userApi.setCreateUser(user.getID());
                userApi.setCategory(getCategory(categorySpinner.getSelectedItem().toString()));
            }
            else userApi.setType(1);
        }

        if (isOnline(this)) {
            if(editableUser != null)
                presenter.editUser(userApi);
            else presenter.setUser(userApi);

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

        if(user != null) {
            String category = categorySpinner.getSelectedItem().toString();
            if(category.equals("مسئول عن:") || categorySpinner.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "من فضلك أختر تخصص المستخدم", Toast.LENGTH_LONG).show();
                valid = false;
            }
        }

        if (name.isEmpty()) {
            userNameEditText.setError("برجاء ادخال اسم المستخدم");
            valid = false;
        } else {
            userNameEditText.setError(null);
        }

        if (editableUser == null && pass.isEmpty()) {
            passwordEditText.setError("برجاء ادخال رقم المرور");
            valid = false;
        } else {
            if (editableUser == null && pass.length() < 3) {
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
            if(this.user != null){
                progressBar.dismiss();
                addButton.setEnabled(true);

                Toast.makeText(RegistrationActivity.this, "تم تسجيل بيانات المستخدم بنجاح", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
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
            }
        } else {
            Toast.makeText(RegistrationActivity.this, "فشل تسجيل المستخدم برجاء ادخال البيانات الصحيحة", Toast.LENGTH_LONG).show();
            progressBar.dismiss();
            addButton.setEnabled(true);
        }
    }

    @Override
    public void users(List<User> users) {

    }

    @Override
    public void message(Boolean response) {
            if(response == true){
                progressBar.dismiss();
                addButton.setEnabled(true);

                Toast.makeText(RegistrationActivity.this, "تم تعديل بيانات المستخدم بنجاح", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(RegistrationActivity.this, "فشل تعديل بيانات المستخدم برجاء ادخال البيانات الصحيحة", Toast.LENGTH_LONG).show();
                progressBar.dismiss();
                addButton.setEnabled(true);
            }
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

    public String getCategory(String selectedItem){
        if(selectedItem.equals("أطباء")) return getString(R.string.doctor);
        else if(selectedItem.equals("متاجر")) return getString(R.string.store);
        else if(selectedItem.equals("فنادق")) return getString(R.string.hotel);
        else if(selectedItem.equals("مطاعم او مقاهي")) return getString(R.string.coffee);
        else return null;
    }
}
