package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.FavouriteApi;

import java.util.List;

public interface FavouriteInterface {
    void favourites(List<FavouriteApi> favouriteApis);
    void message(Boolean response);
    void isFavorite(Boolean b);
    void isFavDeleted(boolean b);
}
