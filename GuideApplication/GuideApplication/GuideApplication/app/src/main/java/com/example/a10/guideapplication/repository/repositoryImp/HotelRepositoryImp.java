package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Hotel;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.HotelRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerHotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelRepositoryImp implements HotelRepository {
    ApiService apiService;
    public HotelRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void hotels(int branchId, final OnRequestCompleteListenerHotel listener) {
        Call<Result<List<Hotel>>> hotels = apiService.hotels(branchId);
        hotels.enqueue(new Callback<Result<List<Hotel>>>() {
            @Override
            public void onResponse(Call<Result<List<Hotel>>> call, Response<Result<List<Hotel>>> response) {
                if (response.body()!=null ){
                    try {
                        Result<List<Hotel>> result = response.body();
                        listener.hotels(result.getData());
                    }catch (Exception e){
                        listener.hotels(null);
                    }
                }else {
                    listener.hotels(null);
                }
            }
            @Override
            public void onFailure(Call<Result<List<Hotel>>> call, Throwable t) {
                listener.hotels(null);
            }
        });
    }

    @Override
    public void addHotel(Hotel hotel, final OnRequestCompleteListenerHotel listener) {
        Call<Result<Hotel>> apiHotel = apiService.addHotel(hotel);
        apiHotel.enqueue(new Callback<Result<Hotel>>() {
            @Override
            public void onResponse(Call<Result<Hotel>> call, Response<Result<Hotel>> response) {
                if (response.body()!=null ){
                    try {
                        Result<Hotel> result = response.body();
                        listener.hotel(result.getData());
                    }catch (Exception e){
                        listener.hotel(null);
                    }
                }else {
                    listener.hotel(null);
                }
            }
            @Override
            public void onFailure(Call<Result<Hotel>> call, Throwable t) {
                listener.hotel(null);
            }
        });
    }

    @Override
    public void editHotel(Hotel hotel, final OnRequestCompleteListenerHotel listener) {
        Call<ApiResult> apiHotel = apiService.editHotel(hotel);
        apiHotel.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
                    ApiResult result = response.body();
                    listener.message(result.isStatusCode());
                }else {
                    listener.message(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.message(null);
            }
        });
    }

    @Override
    public void deleteHotel(int hotelID, final OnRequestCompleteListenerHotel listener) {
        Call<ApiResult> apiHotel = apiService.deleteHotel(hotelID);
        apiHotel.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
                    ApiResult result = response.body();
                    listener.message(result.isStatusCode());
                }else {
                    listener.message(false);
                }
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.message(false);
            }
        });

    }
}
