package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Place;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.repository.PlacesRepository;
import com.example.a10.guideapplication.view.PlaceInterface;

import java.util.List;

public class PlacePresenter {
    PlacesRepository repository;
    PlaceInterface placeInterface;
    public PlacePresenter(PlacesRepository repository, PlaceInterface placeInterface){
        this.repository = repository;
        this.placeInterface = placeInterface;
    }

    public void addPlace(Place place){
        final PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {

            }

            @Override
            public void isPlaceAdded(Section section) {
                placeInterface.isPlaceAdded(section);
            }

            @Override
            public void isPlaceUpdated(boolean b) {

            }

            @Override
            public void isPlaceDeleted(boolean b) {

            }

            @Override
            public void topRated(List sections) {

            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {

            }

            @Override
            public void searchSection(List<Section> section) {

            }
        };
        repository.addPlace(place, placeInterface);
    }

    public void getPlaces(String type){
        PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {
                placeInterface.places(sections);
            }

            @Override
            public void isPlaceAdded(Section section) {

            }

            @Override
            public void isPlaceUpdated(boolean b) {

            }

            @Override
            public void isPlaceDeleted(boolean b) {

            }

            @Override
            public void topRated(List sections) {

            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {

            }

            @Override
            public void searchSection(List<Section> section) {

            }
        };
        repository.getPlaces(type, placeInterface2);
    }

    public void updatePlace(Place place){
        PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {

            }

            @Override
            public void isPlaceAdded(Section section) {

            }

            @Override
            public void isPlaceUpdated(boolean b) {
                placeInterface.isPlaceUpdated(b);
            }

            @Override
            public void isPlaceDeleted(boolean b) {

            }

            @Override
            public void topRated(List sections) {

            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {

            }

            @Override
            public void searchSection(List<Section> section) {

            }
        };
        repository.updatePlace(place, placeInterface2);
    }

    public void deletePlace(int placeId){
        PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {

            }

            @Override
            public void isPlaceAdded(Section section) {

            }

            @Override
            public void isPlaceUpdated(boolean b) {

            }

            @Override
            public void isPlaceDeleted(boolean b) {
                placeInterface.isPlaceDeleted(b);
            }

            @Override
            public void topRated(List sections) {

            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {

            }

            @Override
            public void searchSection(List<Section> section) {

            }
        };
        repository.deletePlace(placeId, placeInterface2);
    }

    public void topRated(String type){
        final PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {

            }

            @Override
            public void isPlaceAdded(Section section) {

            }

            @Override
            public void isPlaceUpdated(boolean b) {

            }

            @Override
            public void isPlaceDeleted(boolean b) {

            }

            @Override
            public void topRated(List sections) {
                placeInterface.topRated(sections);
            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {

            }

            @Override
            public void searchSection(List<Section> section) {

            }
        };
        repository.getTopRated(type, placeInterface2);
    }

    public void search(final String type, String placeName){
        PlaceInterface placeInterface2 = new PlaceInterface() {
            @Override
            public void places(List<Section> sections) {

            }

            @Override
            public void isPlaceAdded(Section section) {

            }

            @Override
            public void isPlaceUpdated(boolean b) {

            }

            @Override
            public void isPlaceDeleted(boolean b) {

            }

            @Override
            public void topRated(List sections) {

            }

            @Override
            public void searchDoctor(List<Doctor> doctor) {
                placeInterface.searchDoctor(doctor);

            }

            @Override
            public void searchSection(List<Section> section) {
                placeInterface.searchSection(section);

            }
        };
        repository.search(type, placeName, placeInterface2);
    }

}
