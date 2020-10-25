package com.haqwat.services;

import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.NationalityDataModel;
import com.haqwat.models.PlaceGeocodeData;
import com.haqwat.models.PlaceMapDetailsData;
import com.haqwat.models.TeamDataModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);


    @GET("api/all-countries")
    Call<NationalityDataModel> getNationality();

    @GET("api/all-leagues")
    Call<LeagueDataModel> getLeague();

    @FormUrlEncoded
    @POST("api/teamsUsingLeagueId")
    Call<TeamDataModel> getTeams(@Field("league_id") int league_id);

}