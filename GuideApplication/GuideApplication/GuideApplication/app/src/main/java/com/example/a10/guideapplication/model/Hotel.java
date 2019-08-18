package com.example.a10.guideapplication.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hotel {
    private int ID;
    private int BranchID;
    private String RoomType;
    private Double Price;
    private String Meals;
    private String CreateDate;
    private int CreateUser;
    private String ModifiedDate;
    private Integer ModifiedUser;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getBranchID() {
        return BranchID;
    }

    public void setBranchID(int branchID) {
        BranchID = branchID;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomType) {
        RoomType = roomType;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getMeals() {
        return Meals;
    }

    public void setMeals(String meals) {
        Meals = meals;
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

    public int getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(int createUser) {
        CreateUser = createUser;
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

    public void setModifiedUser(Integer modifiedUser) {
        ModifiedUser = modifiedUser;
    }
}
