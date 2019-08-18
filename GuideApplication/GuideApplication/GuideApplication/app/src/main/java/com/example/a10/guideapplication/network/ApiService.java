package com.example.a10.guideapplication.network;

import com.example.a10.guideapplication.model.ApiResult;
import com.example.a10.guideapplication.model.Branch;
import com.example.a10.guideapplication.model.BranchApi;
import com.example.a10.guideapplication.model.Doctor;
import com.example.a10.guideapplication.model.DoctorApi;
import com.example.a10.guideapplication.model.Favourite;
import com.example.a10.guideapplication.model.FavouriteApi;
import com.example.a10.guideapplication.model.Hotel;
import com.example.a10.guideapplication.model.Offer;
import com.example.a10.guideapplication.model.Place;
import com.example.a10.guideapplication.model.Result;
import com.example.a10.guideapplication.model.Review;
import com.example.a10.guideapplication.model.ReviewWithOwner;
import com.example.a10.guideapplication.model.Section;
import com.example.a10.guideapplication.model.Token;
import com.example.a10.guideapplication.model.User;
import com.example.a10.guideapplication.model.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @POST("Place")
    Call<Result<Section>> addPlace(@Body Place place);

    @PUT("Place")
    Call<Result<Section>> updatePlace(@Body Place place);

    @GET("Place")
    Call<Result<List<Section>>> getPlaces(@Query("type") String type);

    @DELETE("Place")
    Call<Result<Section>> deletePlace(@Query("sectionId") int sectionId);

    @POST("Reviews")
    Call<Result<Review>> addReview(@Body Review review);

    @GET("Reviews")
    Call<Result<List<ReviewWithOwner>>> getReviews(@Query("sectionId") int sectionId, @Query("type") int type);

    @POST("Offer")
    Call<Result<Offer>> addOffer(@Body Offer offer);

    @GET("Offer")
    Call<Result<List<Offer>>> getPlaceOffers(@Query("sectionId") int sectionId);

    @GET("Offer")
    Call<Result<List<Offer>>> getAllOffers();

    @PUT("Offer")
    Call<Result<Offer>> updateOffer(@Body Offer offer);

    @DELETE("Offer")
    Call<Result<Offer>> deleteOffer(@Query("offerId") int offerId);

    @GET("Login")
    Call<Result<User>> login(@Query("data") String data);

    @POST("Login")
    Call<Result<User>> register(@Body UserApi newUser);

    @PUT("Login")
    Call<ApiResult> editProfile(@Body UserApi newUser);

    @POST("Doctor")
    Call<Result<Doctor>> addDoctor(@Body DoctorApi newDoctor);

    @PUT("Doctor")
    Call<ApiResult> editDoctor(@Body DoctorApi newDoctor);

    @DELETE("Doctor")
    Call<ApiResult> deleteDoctor(@Query("doctorId") int doctorID);

    @GET("Doctor")
    Call<Result<List<Doctor>>> doctors();

    @POST("Branch")
    Call<Result<Branch>> addBranch(@Body BranchApi newBranch);

    @PUT("Branch")
    Call<ApiResult> editBranch(@Body BranchApi newBranch);

    @DELETE("Branch")
    Call<ApiResult> deleteBranch(@Query("branchId") int branchID);

    @GET("Branch")
    Call<Result<List<Branch>>> branches(@Query("sectionId") int sectionID, @Query("type") int type);

    @POST("Hotel")
    Call<Result<Hotel>> addHotel(@Body Hotel newHotel);

    @PUT("Hotel")
    Call<ApiResult> editHotel(@Body Hotel newHotel);

    @DELETE("Hotel")
    Call<ApiResult> deleteHotel(@Query("detailsId") int hotelID);

    @GET("Hotel")
    Call<Result<List<Hotel>>> hotels(@Query("branchId") int branchID);

    @GET("TopRated")
    Call<Result<List<Section>>> topRatedPlaces(@Query("type") String type);

    @GET("TopRated")
    Call<Result<List<Doctor>>> topRatedDoctors(@Query("type") String type);

    @GET("Place")
    Call<Result<List<Section>>> search(@Query("type") String type, @Query("placeName") String placeName);

    @GET("Place")
    Call<Result<List<Doctor>>> searchDoctor(@Query("type") String type, @Query("placeName") String placeName);

    @POST("Favourite")
    Call<ApiResult> addFavourite(@Body Favourite newFavourite);

    @DELETE("Favourite")
    Call<ApiResult> deleteFavourite(@Query("favouriteID") int favouriteID);

    @GET("Favourite")
    Call<Result<List<FavouriteApi>>> favourites(@Query("userID") int userID);

    @GET("Favourite")
    Call<Result> isFavorite(@Query("userID") int userId, @Query("sectionId") int sectionId, @Query("type") int type);

    @DELETE("Favourite")
    Call<Result> deleteFavorite(@Query("sectionID") int sectionId, @Query("userID") int userId, @Query("typeID") int typeID);

    @FormUrlEncoded
    @POST("token")
    Call<Token> getToken(@Field("UserName") String userName, @Field("Password") String password, @Field("grant_type") String grantType);

    @GET("Place")
    Call<Result<List<Section>>> getOwnerPlaces(@Query("places") String places, @Query("type") String userCategory);

    @GET("Doctor")
    Call<Result<List<Doctor>>> doctorsOwner(@Query("places") String places);

    @GET("Login")
    Call<Result<List<User>>> owners();

    @DELETE("Login")
    Call<ApiResult> deleteUser(@Query("userId") int userID, @Query("deleteSection") boolean deleteSection);

}
