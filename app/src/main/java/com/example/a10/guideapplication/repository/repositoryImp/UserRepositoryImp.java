package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.model.UserApi;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerUser;
import com.example.a10.guideapplication.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImp implements UserRepository {
    ApiService apiService;
    public UserRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void getOwners(final OnRequestCompleteListenerUser listener) {
        Call<Result<List<User>>> owners = apiService.owners();
        owners.enqueue(new Callback<Result<List<User>>>() {
            @Override
            public void onResponse(Call<Result<List<User>>> call, Response<Result<List<User>>> response) {
                if (response.body()!=null ){
                    try {
                        Result<List<User>> result = response.body();
                        listener.users(result.getData());
                    }catch (Exception e){
                        listener.users(null);
                    }
                }else{
                    listener.users(null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<User>>> call, Throwable t) {
                listener.users(null);
            }
        });
    }

    @Override
    public void login(String data, final OnRequestCompleteListenerUser listener) {
        Call<Result<User>> user = apiService.login(data);
        user.enqueue(new Callback<Result<User>>() {
            @Override
            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                if (response.body()!=null ){
                    try {
                        Result<User> result = response.body();
                        listener.user(result.getData());
                    }catch (Exception e){
                        listener.user(null);
                    }
                }else{
                    listener.user(null);
                }
            }
            @Override
            public void onFailure(Call<Result<User>> call, Throwable t) {
                listener.user(null);
            }
        });
    }

    @Override
    public void register(UserApi user, final OnRequestCompleteListenerUser listener) {
        Call<Result<User>> apiUser = apiService.register(user);
        apiUser.enqueue(new Callback<Result<User>>() {
            @Override
            public void onResponse(Call<Result<User>> call, Response<Result<User>> response) {
                if (response.body()!=null ){
                    try {
                        Result<User> result = response.body();
                        listener.user(result.getData());
                    }catch (Exception e){
                        listener.user(null);
                    }
                }else{
                    listener.user(null);
                }
            }
            @Override
            public void onFailure(Call<Result<User>> call, Throwable t) {
                listener.user(null);
            }
        });
    }

    @Override
    public void editProfile(UserApi user, final OnRequestCompleteListenerUser listener) {
        Call<ApiResult> editUser = apiService.editProfile(user);
        editUser.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
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
    public void deleteUser(int userID, boolean deleteSection, final OnRequestCompleteListenerUser listener) {
        Call<ApiResult> deleted = apiService.deleteUser(userID, deleteSection);
        deleted.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                if (response.body()!=null ){
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
}
