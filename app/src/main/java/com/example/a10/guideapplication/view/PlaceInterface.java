package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;

import java.util.List;

public interface PlaceInterface {
    void places(List<Section> sections);
    void isPlaceAdded(Section section);
    void isPlaceUpdated(boolean b);
    void isPlaceDeleted(boolean b);
    void topRated(List sections);
    void searchDoctor(List<Doctor> doctor);
    void searchSection(List<Section> section);
}
