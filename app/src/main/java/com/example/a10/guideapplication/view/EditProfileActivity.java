package com.example.a10.guideapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Item;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.model.UserApi;
import com.example.a10.guideapplication.presenter.UserPresenter;
import com.example.a10.guideapplication.repository.repositoryImp.UserRepositoryImp;
import com.example.a10.guideapplication.utils.PrefUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.a10.guideapplication.utils.Utils.isOnline;


public class EditProfileActivity extends AppCompatActivity implements UserInterface{

    @BindView(R.id.toolbar_back)
    Button back;
    @BindView(R.id.toolbar_edit)
    Button edit;
    @BindView(R.id.name)
    EditText nameEditText;
    @BindView(R.id.address)
    EditText addressEditText;
    @BindView(R.id.mail)
    EditText mailEditText;
    @BindView(R.id.phone)
    EditText phoneEditText;
    @BindView(R.id.password)
    EditText passwordEditText;
    @BindView(R.id.job)
    EditText jobEditText;
    @BindView(R.id.imageButton)
    ImageView imageButton;
    @BindView(R.id.imageProfile)
    de.hdodenhof.circleimageview.CircleImageView profileImage;
    UserInterface userInterface;
    UserPresenter presenter;
    User user;
    UserApi userApi = new UserApi();
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        App.setContext(this);

        edit.setBackgroundResource(R.mipmap.done_black);
        edit.setVisibility(View.VISIBLE);

        userInterface = this;
        presenter = new UserPresenter(userInterface, new UserRepositoryImp());

        final Gson gson = new Gson();
        user = gson.fromJson(PrefUtils.getKeys(App.getContext(), getString(R.string.user)), User.class);

        nameEditText.setText(user.getUserName(), TextView.BufferType.EDITABLE);
        mailEditText.setText(user.getMail(), TextView.BufferType.EDITABLE);
        if(user.getPhone() != 0) {
            int phone = user.getPhone();
            phoneEditText.setText(String.valueOf(phone), TextView.BufferType.EDITABLE);
        }
        passwordEditText.setText(user.getPassword(), TextView.BufferType.EDITABLE);

        addressEditText.setText(user.getAddress(), TextView.BufferType.EDITABLE);

        jobEditText.setText(user.getJob(), TextView.BufferType.EDITABLE);

        if(user.getImage() != null) {
            byte[] decodedString = Base64.decode(user.getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImage.setImageBitmap(decodedByte);
            //profileImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length));
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    }

    void save(){
        if (!validate()) {
            return;
        }
        if(nameEditText.getText() != null)
            user.setUserName(nameEditText.getText().toString());

        if(mailEditText.getText() != null)
            user.setMail(mailEditText.getText().toString());

        if(passwordEditText.getText() != null)
            user.setPassword(passwordEditText.getText().toString());

        if(phoneEditText.getText() != null)
            user.setPhone(Integer.valueOf(phoneEditText.getText().toString()));

        if(addressEditText.getText() != null)
            user.setAddress(addressEditText.getText().toString());

        if(jobEditText.getText() != null)
            user.setJob(jobEditText.getText().toString());

        user.setModifiedUser(user.getID());


        if (isOnline(this)) {
            userApi = user.getUserApi();
            presenter.editUser(userApi);
        }else
            Toast.makeText(this, "لا يوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
    }

    private boolean validate () {
        boolean valid = true;
        String name = nameEditText.getText().toString();
        String pass = passwordEditText.getText().toString();
        String mail = mailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (name.isEmpty()) {
            nameEditText.setError("برجاء ادخال اسم المستخدم");
            valid = false;
        } else {
            nameEditText.setError(null);
        }

        if (pass.isEmpty()) {
            passwordEditText.setError("برجاء ادخال رقم المرور");
            valid = false;
        } else {
            if(pass.length() < 3){
                passwordEditText.setError("رقم المرور يجب أن يكون أكثر من 3 حروف");
                valid = false;
            }
            passwordEditText.setError(null);
        }

        if (mail.isEmpty()) {
            mailEditText.setError("برجاء ادخال البريد الإلكتروني");
            valid = false;
        } else {
            if(! Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                mailEditText.setError("برجاء ادخال البريد الإلكتروني بشكل صحيح");
                valid = false;
            }else mailEditText.setError(null);
        }

        if (phone.isEmpty()) {
            phoneEditText.setError("برجاء ادخال رقم الهاتف");
            valid = false;
        } else {
            if(phone.length() != 9){
                phoneEditText.setError("برجاء ادخال رقم الهاتف بشكل صحيح");
                valid = false;
            }else phoneEditText.setError(null);
        }

        return valid;
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);

        final Item[] items = {
                new Item("اختر صورة من البوم الصور",  R.drawable.gallery),
                new Item("التقط صورة من الكاميرا", R.drawable.camera)};

        ListAdapter adapter = new ArrayAdapter<Item>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1,
                items){
            public View getView(final int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                //Put the image on the TextView
                tv.setText(items[position].text);
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);
                return v;
            }
        };
        pictureDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    profileImage.setImageBitmap(bitmap);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
                    byte[] bitmapData = blob.toByteArray();
                    String encoded = Base64.encodeToString(bitmapData, Base64.DEFAULT);
                    user.setImage(encoded);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "فشل تحميل الصورة!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(thumbnail);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            byte[] bitmapData = blob.toByteArray();
            String encoded = Base64.encodeToString(bitmapData, Base64.DEFAULT);
            user.setImage(encoded);
        }
    }

    @Override
    public void user(User user) {

    }

    @Override
    public void users(List<User> users) {

    }

    @Override
    public void message(Boolean response) {
        if (response = true){
            Gson gson = new Gson();
            String json = gson.toJson(user);
            PrefUtils.storeKeys(EditProfileActivity.this, getString(R.string.user), json);
            Toast.makeText(this, "لقد تم تعديل البيانات بنجاح", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "برجاء ادخال بيانات صحيحه والمحاوله مره آخرى", Toast.LENGTH_LONG).show();
        }
    }
}
