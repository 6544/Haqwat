package com.haqwat.services;

import com.haqwat.models.ChargeDataModel;
import com.haqwat.models.HomeModel;
import com.haqwat.models.LeagueDataModel;
import com.haqwat.models.MatchesDataModel;
import com.haqwat.models.NationalityDataModel;
import com.haqwat.models.PlaceGeocodeData;
import com.haqwat.models.PlaceMapDetailsData;
import com.haqwat.models.TeamDataModel;
import com.haqwat.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUp1(@Field("email") String email,
                            @Field("password") String password,
                            @Field("software_type") String software_type,
                            @Field("is_social_media_register") String is_social_media_register
    );

    @FormUrlEncoded
    @POST("api/registerForHaqawat")
    Call<UserModel> signUpWithoutImage2(@Header("Authorization") String bearer_token,
                                        @Field("name") String name,
                                        @Field("country_id") String country_id,
                                        @Field("gender") String gender,
                                        @Field("birthday") String birthday,
                                        @Field("subscribe_method") String subscribe_method,
                                        @Field("subscribe_code") String subscribe_code,
                                        @Field("league_id") String league_id,
                                        @Field("team_id") String team_id
    );

    @Multipart
    @POST("api/registerForHaqawat")
    Call<UserModel> signUpWithImage2(@Header("Authorization") String bearer_token,
                                     @Part("name") RequestBody name,
                                     @Part("country_id") RequestBody country_id,
                                     @Part("gender") RequestBody gender,
                                     @Part("birthday") RequestBody birthday,
                                     @Part("subscribe_method") RequestBody subscribe_method,
                                     @Part("subscribe_code") RequestBody subscribe_code,
                                     @Part("league_id") RequestBody league_id,
                                     @Part("team_id") RequestBody team_id,
                                     @Part MultipartBody.Part image
    );

    @GET("api/sendConfirmCode")
    Call<ResponseBody> sendConfirmationCode(@Header("Authorization") String bearer_token);


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("email") String email,
                          @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/confirmCodeCheck")
    Call<UserModel> checkConfirmationCode(@Header("Authorization") String bearer_token,
                                          @Field("code") String code
    );

    @POST("api/logout")
    Call<ResponseBody> logout(@Header("Authorization") String bearer_token
    );


    @GET("api/home-link")
    Call<HomeModel> getHomeLinks(@Header("Authorization") String bearer_token);

    @GET("api/matchesDisplayInHomePage")
    Call<MatchesDataModel> getCurrentRound(@Header("Authorization") String bearer_token);

    @FormUrlEncoded
    @POST("api/makeMatchExpectation")
    Call<ResponseBody> makeExpectation(@Header("Authorization") String bearer_token,
                                       @Field("match_id") int match_id,
                                       @Field("first_team_goals_count") int first_team_goals_count,
                                       @Field("second_team_goals_count") int second_team_goals_count
    );

    @GET("api/haqawatBalanceDisplay")
    Call<ChargeDataModel> getCharge(@Header("Authorization") String bearer_token);

}