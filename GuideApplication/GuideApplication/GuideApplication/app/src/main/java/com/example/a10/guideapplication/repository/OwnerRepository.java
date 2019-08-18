package com.example.a10.guideapplication.repository;

public interface OwnerRepository {
    void getDoctors(String places, OnRequestCompleteListenerOwner listenerOwner);
    void getSections(String places, String type, OnRequestCompleteListenerOwner listenerOwner);
}
