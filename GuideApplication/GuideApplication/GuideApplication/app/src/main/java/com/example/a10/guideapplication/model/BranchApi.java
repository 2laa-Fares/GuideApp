package com.example.a10.guideapplication.model;

import java.util.List;

public class BranchApi {
    private int ID;
    private int SectionID;
    private String Name;
    private double LocationX;
    private double LocationY;
    private String Address;
    private String Contact;
    private String Photos;
    private String Times;
    private int Type;
    private int CreateUser;
    private Integer ModifiedUser;
    private List<Images> Images;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSectionID() {
        return SectionID;
    }

    public void setSectionID(int sectionID) {
        SectionID = sectionID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLocationX() {
        return LocationX;
    }

    public void setLocationX(double locationX) {
        LocationX = locationX;
    }

    public double getLocationY() {
        return LocationY;
    }

    public void setLocationY(double locationY) {
        LocationY = locationY;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getPhotos() {
        return Photos;
    }

    public void setPhotos(String photos) {
        Photos = photos;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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

    public List<Images> getImages() {
        return Images;
    }

    public void setImages(List<Images> images) {
        Images = images;
    }
}
