package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import java.util.List;

public interface OnRequestCompleteListenerOwner {
    void doctors(List<Doctor> doctors);
    void sections(List<Section> sections);
}
