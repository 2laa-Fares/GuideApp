package com.example.a10.guideapplication.repository;
import com.example.a10.guideapplication.model.BranchApi;

public interface BranchRepository {
    void branches(int sectionId, int type, OnRequestCompleteListenerBranch listener);
    void addBranch(BranchApi branchApi, OnRequestCompleteListenerBranch listener);
    void editBranch(BranchApi branchApi, OnRequestCompleteListenerBranch listener);
    void deleteBranch(int branchID, OnRequestCompleteListenerBranch listener);
}
