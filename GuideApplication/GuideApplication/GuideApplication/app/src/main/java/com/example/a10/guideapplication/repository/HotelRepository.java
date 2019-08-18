package com.example.a10.guideapplication.repository;
import com.example.a10.guideapplication.model.Hotel;

public interface HotelRepository {
    void hotels(int branchId, OnRequestCompleteListenerHotel listener);
    void addHotel(Hotel hotel, OnRequestCompleteListenerHotel listener);
    void editHotel(Hotel hotel, OnRequestCompleteListenerHotel listener);
    void deleteHotel(int hotelID, OnRequestCompleteListenerHotel listener);
}
