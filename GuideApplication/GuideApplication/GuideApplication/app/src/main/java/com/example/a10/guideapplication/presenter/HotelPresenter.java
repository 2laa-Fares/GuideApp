package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Hotel;
import com.example.a10.guideapplication.repository.HotelRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerHotel;
import com.example.a10.guideapplication.view.HotelInterface;

import java.util.List;

public class HotelPresenter {
    HotelInterface hotelInterface;
    HotelRepository repository;
    public HotelPresenter(HotelInterface hotelInterface, HotelRepository repository){
        this.hotelInterface = hotelInterface;
        this.repository = repository;
    }

    public void getHotels(int branchID){
        OnRequestCompleteListenerHotel listener = new OnRequestCompleteListenerHotel() {
            @Override
            public void hotels(List<Hotel> hotels) {
                hotelInterface.hotels(hotels);
            }

            @Override
            public void hotel(Hotel hotel) {

            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.hotels(branchID, listener);
    }

    public void setHotel(Hotel hotel){
        OnRequestCompleteListenerHotel listener = new OnRequestCompleteListenerHotel() {
            @Override
            public void hotels(List<Hotel> hotels) {

            }

            @Override
            public void hotel(Hotel hotel) {
                hotelInterface.hotel(hotel);
            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.addHotel(hotel, listener);
    }

    public void editHotel(Hotel hotel){
        OnRequestCompleteListenerHotel listener = new OnRequestCompleteListenerHotel() {
            @Override
            public void hotels(List<Hotel> hotels) {

            }

            @Override
            public void hotel(Hotel hotel) {

            }

            @Override
            public void message(Boolean response) {
                hotelInterface.message(response);
            }
        };
        repository.editHotel(hotel, listener);
    }

    public void deleteHotel(int hotelID){
        OnRequestCompleteListenerHotel listener = new OnRequestCompleteListenerHotel() {
            @Override
            public void hotels(List<Hotel> hotels) {

            }

            @Override
            public void hotel(Hotel hotel) {

            }

            @Override
            public void message(Boolean response) {
                hotelInterface.message(response);
            }
        };
        repository.deleteHotel(hotelID, listener);
    }
}
