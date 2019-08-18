package com.example.a10.guideapplication.model;

import java.io.Serializable;

public class DoctorApi implements Serializable {

    private int ID;
    private String Name;
    private String Specialization;
    private String Image;
    private float Rate;
    private int CreateUser;
    private Integer ModifiedUser;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }

    public int getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(int createUser) {
        CreateUser = createUser;
    }

    public Integer getModifiedUser() {
        return ModifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        ModifiedUser = modifiedUser;
    }
}