package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Doctor;

import java.util.List;

public interface DoctorInterface {
    void doctor(Doctor doctor);
    void doctorMessage(Boolean response);
    void doctors(List<Doctor> doctors);
}
