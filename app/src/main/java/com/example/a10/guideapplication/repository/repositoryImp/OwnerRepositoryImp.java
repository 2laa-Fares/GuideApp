package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerOwner;
import com.example.a10.guideapplication.repository.OwnerRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerRepositoryImp implements OwnerRepository {
    ApiService apiService;
    public OwnerRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void getDoctors(String places, final OnRequestCompleteListenerOwner listenerOwner) {
        Call<Result<List<Doctor>>> doctorsOwner = apiService.doctorsOwner(places);
        doctorsOwner.enqueue(new Callback<Result<List<Doctor>>>() {
            @Override
            public void onResponse(Call<Result<List<Doctor>>> call, Response<Result<List<Doctor>>> response) {
                if (response.body()!=null ){
                    try {
                        Result<List<Doctor>> result = response.body();
                        listenerOwner.doctors(result.getData());
                    }catch (Exception e){
                        listenerOwner.doctors(null);
                    }
                }else {
                    listenerOwner.doctors(null);
                }
            }
            @Override
            public void onFailure(Call<Result<List<Doctor>>> call, Throwable t) {
                listenerOwner.doctors(null);
            }
        });
    }

    @Override
    public void getSections(String places, String type, final OnRequestCompleteListenerOwner listenerOwner) {
        Call<Result<List<Section>>> sections = apiService.getOwnerPlaces(places, type);
        sections.enqueue(new Callback<Result<List<Section>>>() {
            @Override
            public void onResponse(Call<Result<List<Section>>> call, Response<Result<List<Section>>> response) {
                if(response.body() != null){
                    try{
                        Result<List<Section>> result = response.body();
                        listenerOwner.sections(result.getData());
                    }catch (Exception e){
                        listenerOwner.sections(null);
                    }
                }else{
                    listenerOwner.sections(null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Section>>> call, Throwable t) {
                listenerOwner.sections(null);
            }
        });
    }
}
