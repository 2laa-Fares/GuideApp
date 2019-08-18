package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.repository.OnRequestCompleteListenerOwner;
import com.example.a10.guideapplication.repository.OwnerRepository;
import com.example.a10.guideapplication.view.OwnerInterface;

import java.util.List;

public class OwnerPresenter {
    OwnerInterface ownerInterface;
    OwnerRepository ownerRepository;

    public OwnerPresenter(OwnerInterface ownerInterface, OwnerRepository ownerRepository){
        this.ownerInterface = ownerInterface;
        this.ownerRepository = ownerRepository;
    }

    public void getDoctors(String places){
        OnRequestCompleteListenerOwner listener = new OnRequestCompleteListenerOwner() {
            @Override
            public void doctors(List<Doctor> doctors) {
                ownerInterface.doctors(doctors);
            }

            @Override
            public void sections(List<Section> sections) {

            }
        };
        ownerRepository.getDoctors(places, listener);
    }

    public void getSections(String places, String userCategory){
        OnRequestCompleteListenerOwner listener = new OnRequestCompleteListenerOwner() {
            @Override
            public void doctors(List<Doctor> doctors) {

            }

            @Override
            public void sections(List<Section> sections) {
                ownerInterface.places(sections);
            }
        };
        ownerRepository.getSections(places, userCategory, listener);
    }
}
