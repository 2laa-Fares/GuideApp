package com.example.a10.guideapplication.repository;

import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.view.OffersListener;

public interface OffersRepository {
    void addOffer(Offer offer, OffersListener offersListener);
    void getPlaceOffers(int sectionId, int type, OffersListener offersListener);
    void getAllOffers(OffersListener offersListener);
    void updateOffer(Offer offer, OffersListener offersListener);
    void deleteOffer(int offerId, OffersListener offersListener);
}
