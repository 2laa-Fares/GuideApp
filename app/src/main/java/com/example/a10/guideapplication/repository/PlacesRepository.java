package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Place;
import com.example.a10.guideapplication.view.PlaceInterface;

public interface PlacesRepository {
    void addPlace(Place place, PlaceInterface placeInterface);
    void updatePlace(Place place, PlaceInterface placeInterface);
    void getPlaces(String type, PlaceInterface placeInterface);
    void deletePlace(int placeId, PlaceInterface placeInterface);
    void getTopRated(String type, PlaceInterface placeInterface);
    void search(String type, String placeName, PlaceInterface placeInterface);
}
