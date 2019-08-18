package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Favourite;
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.FavouriteRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerFavourite;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepositoryImp implements FavouriteRepository {

    ApiService apiService;

    public FavouriteRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void favourites(int userID, final OnRequestCompleteListenerFavourite listener) {
        Call<Result<List<FavouriteApi>>> favouriteApis = apiService.favourites(userID);
        favouriteApis.enqueue(new Callback<Result<List<FavouriteApi>>>() {
            @Override
            public void onResponse(Call<Result<List<FavouriteApi>>> call, Response<Result<List<FavouriteApi>>> response) {
                if (response.body()!=null ){
                    Result<List<FavouriteApi>> result = response.body();
                    List<FavouriteApi> favouriteApiList = result.getData();
                    listener.favourites(favouriteApiList);
                }else{
                    listener.favourites(null);
                }
            }
            @Override
            public void onFailure(Call<Result<List<FavouriteApi>>> call, Throwable t) {
                listener.favourites(null);
            }
        });
    }

    @Override
    public void addFavourite(Favourite favourite, final OnRequestCompleteListenerFavourite listener) {
        Call<ApiResult> apiFavourite = apiService.addFavourite(favourite);
        apiFavourite.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ) {
                    ApiResult result = response.body();
                    listener.message(result.isStatusCode());
                }else{
                    listener.message(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.message(false);
            }
        });
    }

    @Override
    public void deleteFavourite(int favouriteID, final OnRequestCompleteListenerFavourite listener) {
        Call<ApiResult> apiFavourite = apiService.deleteFavourite(favouriteID);
        apiFavourite.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
                    ApiResult result = response.body();
                    listener.isFavDeleted(result.isStatusCode());
                }else{
                    listener.isFavDeleted(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.isFavDeleted(false);
            }
        });
    }

    @Override
    public void isFavorite(int userId, int sectionId, int type, final OnRequestCompleteListenerFavourite listener) {
        Call<Result> isFav = apiService.isFavorite(userId, sectionId, type);
        isFav.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body()!=null){
                    listener.isFavorite(response.body().isStatusCode());
                }else {
                    listener.isFavorite(false);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.isFavorite(false);
            }
        });

    }

    @Override
    public void deleteFavorite(int sectionId, int userId, int type, final OnRequestCompleteListenerFavourite listener) {
        Call<Result> deleteFav = apiService.deleteFavorite(sectionId, userId, type);
        deleteFav.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.body()!=null){
                    listener.isFavDeleted(response.body().isStatusCode());
                }else {
                    listener.isFavDeleted(false);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                listener.isFavDeleted(false);
            }
        });
    }
}
