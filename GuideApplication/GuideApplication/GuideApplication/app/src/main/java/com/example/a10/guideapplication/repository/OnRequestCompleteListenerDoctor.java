package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Doctor;

import java.util.List;

public interface OnRequestCompleteListenerDoctor {
    void doctors(List<Doctor> doctor);
    void doctor(Doctor doctor);
    void doctorMessage(Boolean response);
}
