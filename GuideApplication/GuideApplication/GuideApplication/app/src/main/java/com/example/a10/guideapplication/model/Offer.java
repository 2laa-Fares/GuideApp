package com.example.a10.guideapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Offer {
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;
    @SerializedName("SectionName")
    @Expose
    private String sectionName;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Images")
    @Expose
    private String images;
    @SerializedName("MainImage")
    @Expose
    private String mainImage;
    @SerializedName("MDate")
    @Expose
    private String mDate;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("MEndDate")
    @Expose
    private String mEndDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("CreateUser")
    @Expose
    private Integer createUser;
    @SerializedName("ModifiedUser")
    @Expose
    private Integer modifiedUser;
    @SerializedName("ImagesList")
    @Expose
    private List<Images> imagesList = null;

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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Date getMDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(mDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setMDate(String mDate) {
        this.mDate = mDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getMEndDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(mEndDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setMEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Integer modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }
}
