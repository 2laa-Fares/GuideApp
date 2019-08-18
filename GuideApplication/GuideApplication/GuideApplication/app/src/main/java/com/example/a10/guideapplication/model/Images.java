package com.example.a10.guideapplication.model;

import java.io.Serializable;

public class Images implements Serializable {
    private String Name;
    private String Data;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
