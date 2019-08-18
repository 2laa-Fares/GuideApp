package com.example.a10.guideapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Token {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName(".expires")
    @Expose
    private String expireDate;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat
                    ("EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
            inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            // Adjust locale and zone appropriately
            Date date = inputFormat.parse(this.expireDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
