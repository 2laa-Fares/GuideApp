package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Favourite;

public interface FavouriteRepository {
    void favourites(int userID, OnRequestCompleteListenerFavourite listener);
    void addFavourite(Favourite favourite, OnRequestCompleteListenerFavourite listener);
    void deleteFavourite(int favouriteID, OnRequestCompleteListenerFavourite listener);
    void isFavorite(int userId, int sectionId, int type, OnRequestCompleteListenerFavourite  listener);
    void deleteFavorite(int sectionId, int userId, int type, OnRequestCompleteListenerFavourite  listener);
}
