package com.example.a10.guideapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Branch implements Parcelable {
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
    private String CreateDate;
    private int CreateUser;
    private String ModifiedDate;
    private Integer ModifiedUser;

    public Branch() {

    }

    protected Branch(Parcel in) {
        ID = in.readInt();
        SectionID = in.readInt();
        Name = in.readString();
        LocationX = in.readDouble();
        LocationY = in.readDouble();
        Address = in.readString();
        Contact = in.readString();
        Photos = in.readString();
        Times = in.readString();
        Type = in.readInt();
        CreateDate = in.readString();
        CreateUser = in.readInt();
        ModifiedDate = in.readString();
        if (in.readByte() == 0) {
            ModifiedUser = null;
        } else {
            ModifiedUser = in.readInt();
        }
    }

    public static final Creator<Branch> CREATOR = new Creator<Branch>() {
        @Override
        public Branch createFromParcel(Parcel in) {
            return new Branch(in);
        }

        @Override
        public Branch[] newArray(int size) {
            return new Branch[size];
        }
    };

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

    public BranchApi getBranchApi() {
        BranchApi branchApi = new BranchApi();
        branchApi.setID(ID);
        branchApi.setSectionID(SectionID);
        branchApi.setName(Name);
        branchApi.setLocationX(LocationX);
        branchApi.setLocationY(LocationY);
        branchApi.setAddress(Address);
        branchApi.setContact(Contact);
        branchApi.setPhotos(Photos);
        branchApi.setTimes(Times);
        branchApi.setType(Type);
        branchApi.setCreateUser(CreateUser);
        branchApi.setModifiedUser(ModifiedUser);
        return branchApi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeInt(SectionID);
        parcel.writeString(Name);
        parcel.writeDouble(LocationX);
        parcel.writeDouble(LocationY);
        parcel.writeString(Address);
        parcel.writeString(Contact);
        parcel.writeString(Photos);
        parcel.writeString(Times);
        parcel.writeInt(Type);
        parcel.writeString(CreateDate);
        parcel.writeInt(CreateUser);
        parcel.writeString(ModifiedDate);
        if (ModifiedUser == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(ModifiedUser);
        }
    }
}