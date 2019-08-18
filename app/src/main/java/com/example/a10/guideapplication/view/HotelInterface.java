package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Hotel;

import java.util.List;

public interface HotelInterface {
    void hotel(Hotel hotel);
    void message(Boolean response);
    void hotels(List<Hotel> hotels);
}
