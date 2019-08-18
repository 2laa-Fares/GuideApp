package com.example.a10.guideapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Place {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("PlaceName")
    @Expose
    private String placeName;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("Menu")
    @Expose
    private String menu;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Rate")
    @Expose
    private float rate;
    @SerializedName("CreateUser")
    @Expose
    private Integer createUser;
    @SerializedName("ModifiedUser")
    @Expose
    private Integer modifiedUser;
    @SerializedName("MenuImages")
    @Expose
    private List<Images> menuImages = null;

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
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

    public List<Images> getMenuImages() {
        return menuImages;
    }

    public void setMenuImages(List<Images> menuImages) {
        this.menuImages = menuImages;
    }
}
