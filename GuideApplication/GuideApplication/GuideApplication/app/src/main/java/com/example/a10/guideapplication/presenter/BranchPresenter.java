package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.BranchApi;
import com.example.a10.guideapplication.repository.BranchRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerBranch;
import com.example.a10.guideapplication.view.BranchInterface;

import java.util.List;

public class BranchPresenter {
    BranchInterface branchInterface;
    BranchRepository repository;
    public BranchPresenter(BranchInterface branchInterface, BranchRepository repository){
        this.branchInterface = branchInterface;
        this.repository = repository;
    }

    public void getBranches(int sectionId, int type){
        OnRequestCompleteListenerBranch listener = new OnRequestCompleteListenerBranch() {
            @Override
            public void branches(List<Branch> branches) {
                branchInterface.branches(branches);
            }

            @Override
            public void branch(Branch branch) {

            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.branches(sectionId, type, listener);
    }

    public void setBranch(BranchApi branch){
        OnRequestCompleteListenerBranch listener = new OnRequestCompleteListenerBranch() {
            @Override
            public void branches(List<Branch> branches) {

            }

            @Override
            public void branch(Branch branch) {
                branchInterface.branch(branch);
            }

            @Override
            public void message(Boolean response) {

            }
        };
        repository.addBranch(branch, listener);
    }

    public void editBranch(BranchApi branch){
        OnRequestCompleteListenerBranch listener = new OnRequestCompleteListenerBranch() {
            @Override
            public void branches(List<Branch> branches) {

            }

            @Override
            public void branch(Branch branch) {

            }

            @Override
            public void message(Boolean response) {
                branchInterface.message(response);
            }
        };
        repository.editBranch(branch, listener);
    }

    public void deleteBranch(int branchID){
        OnRequestCompleteListenerBranch listener = new OnRequestCompleteListenerBranch() {
            @Override
            public void branches(List<Branch> branches) {

            }

            @Override
            public void branch(Branch branch) {

            }

            @Override
            public void message(Boolean response) {
                branchInterface.message(response);
            }
        };
        repository.deleteBranch(branchID, listener);
    }
}
