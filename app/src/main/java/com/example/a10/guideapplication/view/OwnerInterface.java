package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import java.util.List;

public interface OwnerInterface {
    void doctors(List<Doctor> doctors);
    void places(List<Section> sections);
}
