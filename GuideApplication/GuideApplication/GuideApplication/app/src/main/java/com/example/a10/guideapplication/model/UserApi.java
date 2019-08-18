package com.example.a10.guideapplication.model;

public class UserApi {
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
    private Integer CreateUser;
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

    public Integer getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(Integer createUser) {
        CreateUser = createUser;
    }

    public Integer getModifiedUser() {
        return ModifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        ModifiedUser = modifiedUser;
    }
}
