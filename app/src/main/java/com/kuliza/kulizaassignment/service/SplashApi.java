package com.kuliza.kulizaassignment.service;

import com.kuliza.kulizaassignment.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Model interface to control the data loading
 */
public interface SplashApi {

    // Forecast method
    String FORECAST = "forecast.json";

    /**
     * Get Forecast Data
     *
     * @param key  key
     * @param q    q
     * @param days days
     * @return JsonObject
     */
    @Headers({"Content-Type: application/json"})
    @GET(FORECAST)
    Call<ForecastResponse> getForecast(@Query("key") String key, @Query("q") String q, @Query("days") String days);
}
