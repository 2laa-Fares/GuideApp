package com.example.a10.guideapplication.repository.repositoryImp;

import com.example.a10.guideapplication.app.App;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.network.ApiClient;
import com.example.a10.guideapplication.network.ApiService;
import com.example.a10.guideapplication.repository.OffersRepository;
import com.example.a10.guideapplication.view.OffersListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersRepositoryImp implements OffersRepository {
    ApiService apiService;

    public OffersRepositoryImp() {
        apiService = ApiClient.getClient(App.getContext()).create(ApiService.class);
    }

    @Override
    public void addOffer(final Offer offer, final OffersListener offersListener) {
        Call<Result<Offer>> addOffer = apiService.addOffer(offer);
        addOffer.enqueue(new Callback<Result<Offer>>() {
            @Override
            public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                if (response.body() != null) {
                    if (response.body().getData()!=null){
                        offersListener.offerAdded(response.body().getData());
                    }else {
                        offersListener.offerAdded(null);
                    }
                } else {
                    offersListener.offerAdded(null);
                }
            }

            @Override
            public void onFailure(Call<Result<Offer>> call, Throwable t) {
                offersListener.offerAdded(null);
            }
        });

    }

    @Override
    public void getPlaceOffers(int sectionId, int type, final OffersListener offersListener) {
        Call<Result<List<Offer>>> placesOffers = apiService.getPlaceOffers(sectionId, type);
        placesOffers.enqueue(new Callback<Result<List<Offer>>>() {
            @Override
            public void onResponse(Call<Result<List<Offer>>> call, Response<Result<List<Offer>>> response) {
                if (response.body() != null) {
                    if (response.body().getData()!=null){
                        offersListener.sectionOffers(response.body().getData());
                    }else {
                        offersListener.sectionOffers(null);
                    }
                } else {
                    offersListener.sectionOffers(null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Offer>>> call, Throwable t) {
                offersListener.sectionOffers(null);
            }
        });
    }

    @Override
    public void getAllOffers(final OffersListener offersListener) {
        Call<Result<List<Offer>>> placesOffers = apiService.getAllOffers();
        placesOffers.enqueue(new Callback<Result<List<Offer>>>() {
            @Override
            public void onResponse(Call<Result<List<Offer>>> call, Response<Result<List<Offer>>> response) {
                if (response.body() != null) {
                    if (response.body().getData()!=null){
                        offersListener.allOffers(response.body().getData());
                    }else {
                        offersListener.allOffers(null);
                    }
                } else {
                    offersListener.allOffers(null);
                }
            }

            @Override
            public void onFailure(Call<Result<List<Offer>>> call, Throwable t) {
                offersListener.allOffers(null);
            }
        });
    }

    @Override
    public void updateOffer(final Offer offer, final OffersListener offersListener) {
        Call<Result<Offer>> updateOffer = apiService.updateOffer(offer);
        updateOffer.enqueue(new Callback<Result<Offer>>() {
            @Override
            public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                if (response.body() != null) {
                    if (response.body().isStatusCode()){
                        offersListener.offerUpdated(true);
                    }else {
                        offersListener.offerUpdated(false);
                    }
                } else {
                    offersListener.offerUpdated(false);
                }
            }

            @Override
            public void onFailure(Call<Result<Offer>> call, Throwable t) {
                offersListener.offerUpdated(false);
            }
        });
    }

    @Override
    public void deleteOffer(int offerId, final OffersListener offersListener) {
        Call<Result<Offer>> deleteOffer = apiService.deleteOffer(offerId);
        deleteOffer.enqueue(new Callback<Result<Offer>>() {
            @Override
            public void onResponse(Call<Result<Offer>> call, Response<Result<Offer>> response) {
                if (response.body() != null) {
                    if (response.body().isStatusCode()){
                        offersListener.offerDeleted(true);
                    }else {
                        offersListener.offerDeleted(false);
                    }
                } else {
                    offersListener.offerDeleted(false);
                }
            }

            @Override
            public void onFailure(Call<Result<Offer>> call, Throwable t) {
                offersListener.offerDeleted(false);
            }
        });
    }
}
