package com.example.a10.guideapplication.view;

import com.example.a10.guideapplication.model.Offer;

import java.util.List;

public interface OffersListener {
    void sectionOffers(List<Offer> offers);
    void allOffers(List<Offer> offers);
    void offerAdded(Offer offer);
    void offerUpdated(Boolean b);
    void offerDeleted(Boolean b);
}
