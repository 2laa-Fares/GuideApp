package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.DoctorApi;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.DoctorRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerDoctor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepositoryImp implements DoctorRepository {
    ApiService apiService;
    public DoctorRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void doctors(final OnRequestCompleteListenerDoctor listener) {
        Call<Result<List<Doctor>>> doctors = apiService.doctors();
        doctors.enqueue(new Callback<Result<List<Doctor>>>() {
            @Override
            public void onResponse(Call<Result<List<Doctor>>> call, Response<Result<List<Doctor>>> response) {
                if (response.body()!=null ){
                    try {
                        Result<List<Doctor>> result = response.body();
                        listener.doctors(result.getData());
                    }catch (Exception e){
                        listener.doctors(null);
                    }
                }else
                    listener.doctors(null);
            }
            @Override
            public void onFailure(Call<Result<List<Doctor>>> call, Throwable t) {
                listener.doctors(null);
            }
        });
    }

    @Override
    public void addDoctor(DoctorApi doctor, final OnRequestCompleteListenerDoctor listener) {
        Call<Result<Doctor>> apiDoctor = apiService.addDoctor(doctor);
        apiDoctor.enqueue(new Callback<Result<Doctor>>() {
            @Override
            public void onResponse(Call<Result<Doctor>> call, Response<Result<Doctor>> response) {
                if (response.body()!=null ){
                    try {
                        Result<Doctor> result = response.body();
                        listener.doctor(result.getData());
                    }catch (Exception e){
                        listener.doctor(null);
                    }
                }else
                    listener.doctor(null);
            }
            @Override
            public void onFailure(Call<Result<Doctor>> call, Throwable t) {
                listener.doctor(null);
            }
        });
    }

    @Override
    public void editDoctor(DoctorApi doctor, final OnRequestCompleteListenerDoctor listener) {
        Call<ApiResult> apiDoctor = apiService.editDoctor(doctor);
        apiDoctor.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
                    ApiResult result = response.body();
                    listener.doctorMessage(result.isStatusCode());
                }else listener.doctorMessage(false);
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.doctorMessage(null);
            }
        });
    }

    @Override
    public void deleteDoctor(int doctorID, final OnRequestCompleteListenerDoctor listener) {
        Call<ApiResult> apiDoctor = apiService.deleteDoctor(doctorID);
        apiDoctor.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
                    ApiResult result = response.body();
                    listener.doctorMessage(result.isStatusCode());
                }else listener.doctorMessage(false);
            }
            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {
                listener.doctorMessage(false);
            }
        });

    }
}
