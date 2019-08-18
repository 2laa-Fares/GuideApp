package com.example.a10.guideapplication.presenter;

import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.repository.OffersRepository;
import com.example.a10.guideapplication.view.OffersListener;

import java.util.List;

public class OffersPresenter {
    OffersListener listener;
    OffersRepository repository;
    public OffersPresenter(OffersListener listener, OffersRepository repository){
        this.listener = listener;
        this.repository = repository;
    }

    public void getSectionOffers(int id, int type){
        OffersListener offersListener = new OffersListener() {
            @Override
            public void sectionOffers(List<Offer> offers) {
                listener.sectionOffers(offers);
            }

            @Override
            public void allOffers(List<Offer> offers) {

            }

            @Override
            public void offerAdded(Offer offer) {

            }

            @Override
            public void offerUpdated(Boolean b) {

            }

            @Override
            public void offerDeleted(Boolean b) {

            }
        };
        repository.getPlaceOffers(id, type, offersListener);
    }

    public void getOffers(){
        OffersListener offersListener = new OffersListener() {
            @Override
            public void sectionOffers(List<Offer> offers) {

            }

            @Override
            public void allOffers(List<Offer> offers) {
                listener.allOffers(offers);
            }

            @Override
            public void offerAdded(Offer offer) {

            }

            @Override
            public void offerUpdated(Boolean b) {

            }

            @Override
            public void offerDeleted(Boolean b) {

            }
        };
        repository.getAllOffers(offersListener);
    }

    public void updateOffer(Offer offer){
        OffersListener offersListener = new OffersListener() {
            @Override
            public void sectionOffers(List<Offer> offers) {

            }

            @Override
            public void allOffers(List<Offer> offers) {

            }

            @Override
            public void offerAdded(Offer offer) {

            }

            @Override
            public void offerUpdated(Boolean b) {
                listener.offerUpdated(b);
            }

            @Override
            public void offerDeleted(Boolean b) {

            }
        };
        repository.updateOffer(offer, offersListener);
    }

    public void addOffer(Offer offer){
        OffersListener offersListener = new OffersListener() {
            @Override
            public void sectionOffers(List<Offer> offers) {

            }

            @Override
            public void allOffers(List<Offer> offers) {

            }

            @Override
            public void offerAdded(Offer offer) {
                listener.offerAdded(offer);
            }

            @Override
            public void offerUpdated(Boolean b) {

            }

            @Override
            public void offerDeleted(Boolean b) {

            }
        };
        repository.addOffer(offer, offersListener);
    }

    public void deleteOffer(int id){
        OffersListener offersListener = new OffersListener() {
            @Override
            public void sectionOffers(List<Offer> offers) {

            }

            @Override
            public void allOffers(List<Offer> offers) {

            }

            @Override
            public void offerAdded(Offer offer) {

            }

            @Override
            public void offerUpdated(Boolean b) {

            }

            @Override
            public void offerDeleted(Boolean b) {
                listener.offerDeleted(b);
            }
        };
        repository.deleteOffer(id, offersListener);
    }
}
