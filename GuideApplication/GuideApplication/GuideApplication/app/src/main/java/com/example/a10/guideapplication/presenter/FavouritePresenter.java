package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Favourite;
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.repository.FavouriteRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerFavourite;
import com.example.a10.guideapplication.view.FavouriteInterface;

import java.util.List;

public class FavouritePresenter {
    FavouriteInterface favouriteInterface;
    FavouriteRepository repository;

    public FavouritePresenter(FavouriteInterface favouriteInterface, FavouriteRepository repository) {
        this.favouriteInterface = favouriteInterface;
        this.repository = repository;
    }

    public void getFavourites(int userID) {
        OnRequestCompleteListenerFavourite listener = new OnRequestCompleteListenerFavourite() {
            @Override
            public void favourites(List<FavouriteApi> favouriteApis) {
                favouriteInterface.favourites(favouriteApis);
            }

            @Override
            public void message(Boolean response) {

            }

            @Override
            public void isFavorite(Boolean b) {

            }

            @Override
            public void isFavDeleted(boolean b) {

            }
        };
        repository.favourites(userID, listener);
    }

    public void setFavourite(Favourite favourite) {
        final OnRequestCompleteListenerFavourite listener = new OnRequestCompleteListenerFavourite() {
            @Override
            public void favourites(List<FavouriteApi> favouriteApis) {

            }

            @Override
            public void message(Boolean response) {
                favouriteInterface.message(response);
            }

            @Override
            public void isFavorite(Boolean b) {

            }

            @Override
            public void isFavDeleted(boolean b) {

            }
        };
        repository.addFavourite(favourite, listener);
    }

    public void deleteFavourite(int favouriteID) {
        OnRequestCompleteListenerFavourite listener = new OnRequestCompleteListenerFavourite() {
            @Override
            public void favourites(List<FavouriteApi> favouriteApis) {

            }

            @Override
            public void message(Boolean response){
            }

            @Override
            public void isFavorite(Boolean b) {

            }

            @Override
            public void isFavDeleted(boolean b) {
                favouriteInterface.isFavDeleted(b);
            }
        };
        repository.deleteFavourite(favouriteID, listener);
    }

    public void deleteFavorite(int sectionId, int userId, int type) {

        OnRequestCompleteListenerFavourite listener = new OnRequestCompleteListenerFavourite() {
            @Override
            public void favourites(List<FavouriteApi> favouriteApis) {

            }

            @Override
            public void message(Boolean response){
            }

            @Override
            public void isFavorite(Boolean b) {

            }

            @Override
            public void isFavDeleted(boolean b) {
                favouriteInterface.isFavDeleted(b);
            }
        };
        repository.deleteFavorite(sectionId, userId, type, listener);
    }

    public void isFavorite(int userId, int sectionId, int type) {
        OnRequestCompleteListenerFavourite listener = new OnRequestCompleteListenerFavourite() {
            @Override
            public void favourites(List<FavouriteApi> favouriteApis) {

            }

            @Override
            public void message(Boolean response) {

            }

            @Override
            public void isFavorite(Boolean b) {
                favouriteInterface.isFavorite(b);
            }

            @Override
            public void isFavDeleted(boolean b) {

            }
        };
        repository.isFavorite(userId, sectionId, type, listener);
    }
}
