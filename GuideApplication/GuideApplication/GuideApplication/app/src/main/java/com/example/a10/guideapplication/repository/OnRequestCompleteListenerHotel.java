package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Hotel;

import java.util.List;

public interface OnRequestCompleteListenerHotel {
    void hotels(List<Hotel> hotels);
    void hotel(Hotel hotel);
    void message(Boolean response);
}
