package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.BranchApi;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.BranchRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerBranch;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchRepositoryImp implements BranchRepository {
    ApiService apiService;
    public BranchRepositoryImp(){
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void branches(int sectionId, int type, final OnRequestCompleteListenerBranch listener) {
        Call<Result<List<Branch>>> branches = apiService.branches(sectionId, type);
        branches.enqueue(new Callback<Result<List<Branch>>>() {
            @Override
            public void onResponse(Call<Result<List<Branch>>> call, Response<Result<List<Branch>>> response) {
                if (response.body()!=null ){
                    Result<List<Branch>> result = response.body();
                    List<Branch> branchList = result.getData();
                    listener.branches(branchList);
                }else{
                    listener.branches(null);
                }
            }
            @Override
            public void onFailure(Call<Result<List<Branch>>> call, Throwable t) {
                listener.branches(null);
            }
        });
    }

    @Override
    public void addBranch(BranchApi branch, final OnRequestCompleteListenerBranch listener) {
        Call<Result<Branch>> apiBranch = apiService.addBranch(branch);
        apiBranch.enqueue(new Callback<Result<Branch>>() {
            @Override
            public void onResponse(Call<Result<Branch>> call, Response<Result<Branch>> response) {
                if (response.body()!=null ){
                    try {
                        Result<Branch> result = response.body();
                        listener.branch(result.getData());
                    }catch (Exception e){
                        listener.branch(null);
                    }
                }else{
                    listener.branch(null);
                }
            }
            @Override
            public void onFailure(Call<Result<Branch>> call, Throwable t) {
                listener.branch(null);
            }
        });
    }

    @Override
    public void editBranch(BranchApi branch, final OnRequestCompleteListenerBranch listener) {
        Call<ApiResult> apiBranch = apiService.editBranch(branch);
        apiBranch.enqueue(new Callback<ApiResult>() {
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
                listener.message(null);
            }
        });
    }

    @Override
    public void deleteBranch(int branchID, final OnRequestCompleteListenerBranch listener) {
        Call<ApiResult> apiBranch = apiService.deleteBranch(branchID);
        apiBranch.enqueue(new Callback<ApiResult>() {
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
