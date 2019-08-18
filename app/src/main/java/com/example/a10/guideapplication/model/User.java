package com.example.a10.guideapplication.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable {

    private int ID;
    private String UserName;
    private String Password;
    private String Mail;
    private int Phone;
    private String Image;
    private int Type;
    private String Category;
    private String Job;
    private String Address;
    private String Places;
    private String CreateDate;
    private Integer CreateUser;
    private String ModifiedDate;
    private Integer ModifiedUser;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPlaces() {
        return Places;
    }

    public void setPlaces(String places) {
        Places = places;
    }

    public void setCreateUser(Integer createUser) {
        CreateUser = createUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        ModifiedUser = modifiedUser;
    }

    public Date getCreateDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(CreateDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public Integer getCreateUser() {
        return CreateUser;
    }

    public Date getModifiedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(ModifiedDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public Integer getModifiedUser() {
        return ModifiedUser;
    }

    public UserApi getUserApi(){
        UserApi userApi = new UserApi();
        userApi.setID(this.ID);
        userApi.setUserName(this.UserName);
        userApi.setPassword(this.Password);
        userApi.setMail(this.Mail);
        userApi.setPhone(this.Phone);
        userApi.setImage(this.getImage());
        userApi.setType(this.Type);
        userApi.setCategory(this.Category);
        userApi.setJob(this.Job);
        userApi.setAddress(this.Address);
        userApi.setPlaces(this.Places);
        userApi.setCreateUser(this.CreateUser);
        userApi.setModifiedUser(this.ModifiedUser);
        return userApi;
    }
}
