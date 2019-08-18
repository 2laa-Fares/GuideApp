package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.R;
import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Place;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.PlacesRepository;
import com.example.a10.guideapplication.view.PlaceInterface;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepositoryImp implements PlacesRepository {
    ApiService apiService;
    public PlacesRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }
    @Override
    public void addPlace(final Place place, final PlaceInterface placeInterface) {
        Call<Result<Section>> addPlace = apiService.addPlace(place);
        addPlace.enqueue(new Callback<Result<Section>>() {
            @Override
            public void onResponse(Call<Result<Section>> call, Response<Result<Section>> response) {
                if (response.body()!=null){
                    if (response.body().getData()!=null){
                        placeInterface.isPlaceAdded(response.body().getData());
                    }else {
                        placeInterface.isPlaceAdded(null);
                    }
                }else {
                    placeInterface.isPlaceAdded(null);
                }
            }
            @Override
            public void onFailure(Call<Result<Section>> call, Throwable t) {
                placeInterface.isPlaceAdded(null);
            }
        });
    }

    @Override
    public void updatePlace(final Place place, final PlaceInterface placeInterface){
        Call<Result<Section>> updatePlace = apiService.updatePlace(place);
        updatePlace.enqueue(new Callback<Result<Section>>() {
            @Override
            public void onResponse(Call<Result<Section>> call, Response<Result<Section>> response) {
                if (response.body()!=null){
                    placeInterface.isPlaceUpdated(response.body().isStatusCode());
                }else {
                    placeInterface.isPlaceUpdated(false);
                }
            }
            @Override
            public void onFailure(Call<Result<Section>> call, Throwable t) {
                placeInterface.isPlaceUpdated(false);
            }
        });
    }

    @Override
    public void getPlaces(String type, final PlaceInterface placeInterface) {
        Call<Result<List<Section>>> getPlaces = apiService.getPlaces(type);
        getPlaces.enqueue(new Callback<Result<List<Section>>>() {
            @Override
            public void onResponse(Call<Result<List<Section>>> call, Response<Result<List<Section>>> response) {
                if (response.body() != null){
                    if (response.body().getCount()!=0 && response.body().getData()!=null){
                        placeInterface.places(response.body().getData());
                    }else {
                        placeInterface.places(null);
                    }
                }else {
                    placeInterface.places(null);
                }
            }
            @Override
            public void onFailure(Call<Result<List<Section>>> call, Throwable t) {
                placeInterface.places(null);
            }
        });
    }

    @Override
    public void deletePlace(int placeId, final PlaceInterface placeInterface) {
        Call<Result<Section>> deletePlace = apiService.deletePlace(placeId);
        deletePlace.enqueue(new Callback<Result<Section>>() {
            @Override
            public void onResponse(Call<Result<Section>> call, Response<Result<Section>> response) {
                if (response.body()!=null){
                    placeInterface.isPlaceDeleted(response.body().isStatusCode());
                }else {
                    placeInterface.isPlaceDeleted(false);
                }
            }
            @Override
            public void onFailure(Call<Result<Section>> call, Throwable t) {
                placeInterface.isPlaceDeleted(false);
            }
        });
    }

    @Override
    public void getTopRated(String type, final PlaceInterface placeInterface) {
        if (type.equals(App.getContext().getString(R.string.doctor))){
            final Call<Result<List<Doctor>>> topRated = apiService.topRatedDoctors(type);
            topRated.enqueue(new Callback<Result<List<Doctor>>>() {
                @Override
                public void onResponse(Call<Result<List<Doctor>>> call, Response<Result<List<Doctor>>> response) {
                    if (response.body()!=null){
                        placeInterface.topRated(response.body().getData());
                    }else {
                        placeInterface.topRated(null);
                    }
                }
                @Override
                public void onFailure(Call<Result<List<Doctor>>> call, Throwable t) {
                    placeInterface.topRated(null);
                }
            });
        }else {
            final Call<Result<List<Section>>> topRated = apiService.topRatedPlaces(type);
            topRated.enqueue(new Callback<Result<List<Section>>>() {
                @Override
                public void onResponse(Call<Result<List<Section>>> call, Response<Result<List<Section>>> response) {
                    if (response.body()!=null){
                        placeInterface.topRated(response.body().getData());
                    }else {
                        placeInterface.topRated(null);
                    }
                }
                @Override
                public void onFailure(Call<Result<List<Section>>> call, Throwable t) {
                    placeInterface.topRated(null);
                }
            });
        }

    }

    @Override
    public void search(final String type, String placeName, final PlaceInterface placeInterface) {
        if (type.equals(App.getContext().getString(R.string.doctor))){
            Call<Result<List<Doctor>>> search = apiService.searchDoctor(type, placeName);
            search.enqueue(new Callback<Result<List<Doctor>>>() {
                @Override
                public void onResponse(Call<Result<List<Doctor>>> call, Response<Result<List<Doctor>>> response) {
                    if (response.body()!=null){
                        if (response.body().getCount()>0){
                            placeInterface.searchDoctor(response.body().getData());
                        }else {
                            placeInterface.searchDoctor(null);
                        }
                    }else {
                        placeInterface.searchDoctor(null);
                    }
                }

                @Override
                public void onFailure(Call<Result<List<Doctor>>> call, Throwable t) {
                    placeInterface.searchDoctor(null);
                }
            });
        }else {
            Call<Result<List<Section>>> search = apiService.search(type, placeName);
            search.enqueue(new Callback<Result<List<Section>>>() {
                @Override
                public void onResponse(Call<Result<List<Section>>> call, Response<Result<List<Section>>> response) {
                    if (response.body()!=null){
                        if (response.body().getCount()>0){
                            placeInterface.searchSection(response.body().getData());
                        }else {
                            placeInterface.searchSection(null);
                        }
                    }else {
                        placeInterface.searchSection(null);
                    }
                }

                @Override
                public void onFailure(Call<Result<List<Section>>> call, Throwable t) {
                    placeInterface.searchSection(null);
                }
            });
        }

    }


}
