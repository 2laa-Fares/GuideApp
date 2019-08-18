package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Branch;

import java.util.List;

public interface BranchInterface {
    void branch(Branch branch);
    void message(Boolean response);
    void branches(List<Branch> branches);
}
