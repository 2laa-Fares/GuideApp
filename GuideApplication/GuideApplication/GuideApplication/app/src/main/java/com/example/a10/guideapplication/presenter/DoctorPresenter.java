package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.DoctorApi;
import com.example.a10.guideapplication.repository.DoctorRepository;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerDoctor;
import com.example.a10.guideapplication.view.DoctorInterface;

import java.util.List;

public class DoctorPresenter {
    DoctorInterface doctorInterface;
    DoctorRepository repository;
    public DoctorPresenter(DoctorInterface doctorInterface, DoctorRepository repository){
        this.doctorInterface = doctorInterface;
        this.repository = repository;
    }

    public void getDoctors(){
        OnRequestCompleteListenerDoctor listener = new OnRequestCompleteListenerDoctor() {
            @Override
            public void doctors(List<Doctor> doctors) {
                doctorInterface.doctors(doctors);
            }

            @Override
            public void doctor(Doctor doctor) {

            }

            @Override
            public void doctorMessage(Boolean response) {

            }
        };
        repository.doctors(listener);
    }

    public void setDoctor(DoctorApi doctor){
        OnRequestCompleteListenerDoctor listener = new OnRequestCompleteListenerDoctor() {
            @Override
            public void doctors(List<Doctor> doctors) {

            }

            @Override
            public void doctor(Doctor doctor) {
                doctorInterface.doctor(doctor);
            }

            @Override
            public void doctorMessage(Boolean response) {

            }
        };
        repository.addDoctor(doctor, listener);
    }

    public void editDoctor(DoctorApi doctor){
        OnRequestCompleteListenerDoctor listener = new OnRequestCompleteListenerDoctor() {
            @Override
            public void doctors(List<Doctor> doctors) {

            }

            @Override
            public void doctor(Doctor doctor) {

            }

            @Override
            public void doctorMessage(Boolean response) {
                doctorInterface.doctorMessage(response);
            }
        };
        repository.editDoctor(doctor, listener);
    }

    public void deleteDoctor(int doctorID){
        OnRequestCompleteListenerDoctor listener = new OnRequestCompleteListenerDoctor() {
            @Override
            public void doctors(List<Doctor> doctors) {

            }

            @Override
            public void doctor(Doctor doctor) {

            }

            @Override
            public void doctorMessage(Boolean response) {
                doctorInterface.doctorMessage(response);
            }
        };
        repository.deleteDoctor(doctorID, listener);
    }
}
