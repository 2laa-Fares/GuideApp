package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.DoctorApi;

public interface DoctorRepository {
    void doctors(OnRequestCompleteListenerDoctor listener);
    void addDoctor(DoctorApi doctor, OnRequestCompleteListenerDoctor listener);
    void editDoctor(DoctorApi doctor, OnRequestCompleteListenerDoctor listener);
    void deleteDoctor(int doctorID, OnRequestCompleteListenerDoctor listener);
}
