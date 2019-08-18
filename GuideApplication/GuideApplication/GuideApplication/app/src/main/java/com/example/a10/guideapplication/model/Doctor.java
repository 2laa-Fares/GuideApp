package com.example.a10.guideapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Doctor implements Parcelable{

    private int ID;
    private String Name;
    private String Specialization;
    private String Image;
    private Float Rate;
    private String CreateDate;
    private int CreateUser;
    private String ModifiedDate;
    private Integer ModifiedUser;

    public Doctor(Parcel in) {
        ID = in.readInt();
        Name = in.readString();
        Specialization = in.readString();
        Image = in.readString();
        if (in.readByte() == 0) {
            Rate = null;
        } else {
            Rate = in.readFloat();
        }
        CreateDate = in.readString();
        CreateUser = in.readInt();
        ModifiedDate = in.readString();
        if (in.readByte() == 0) {
            ModifiedUser = null;
        } else {
            ModifiedUser = in.readInt();
        }
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };

    public Doctor(Integer id, String placeName, String type, String image, Float rate) {
        this.ID = id;
        this.Name = placeName;
        this.Specialization = type;
        this.Image = image;
        this.Rate = rate;
    }

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

    public Float getRate() {
        return Rate;
    }

    public void setRate(Float rate) {
        Rate = rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Name);
        parcel.writeString(Specialization);
        parcel.writeString(Image);
        if (Rate == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(Rate);
        }
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

