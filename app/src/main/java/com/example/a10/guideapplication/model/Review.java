package com.example.a10.guideapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("Details")
    @Expose
    private String details;
    @SerializedName("Rate")
    @Expose
    private Double rate;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
