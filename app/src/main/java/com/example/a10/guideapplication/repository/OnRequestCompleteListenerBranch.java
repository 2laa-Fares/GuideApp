package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Branch;

import java.util.List;

public interface OnRequestCompleteListenerBranch {
    void branches(List<Branch> branches);
    void branch(Branch branch);
    void message(Boolean response);
}
