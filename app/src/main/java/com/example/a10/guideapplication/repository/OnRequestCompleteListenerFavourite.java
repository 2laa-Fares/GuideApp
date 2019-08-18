package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.FavouriteApi;

import java.util.List;

public interface OnRequestCompleteListenerFavourite {
    void favourites(List<FavouriteApi> favouriteApis);
    void message(Boolean response);
    void isFavorite(Boolean b);
    void isFavDeleted(boolean b);
}
