package com.kuliza.kulizaassignment;

import android.support.annotation.NonNull;

import com.kuliza.kulizaassignment.model.ForecastResponse;
import com.kuliza.kulizaassignment.service.SplashApi;
import com.kuliza.kulizaassignment.util.CDef;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;

/**
 * Forecast Unit test case
 */
public class ForecastUnitTest {
    @Test
    public void loadForecastData() throws Exception {
        SplashApi interactor = KulizaApplication.getInstance().getRetrofit().create(SplashApi.class);
        Call<ForecastResponse> forecastCall = interactor.getForecast(CDef.API_KEY.getValue(), CDef.PLACE.getValue(), CDef.DAYS.getValue());
        forecastCall.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResponse> call, @NonNull Response<ForecastResponse> response) {
                assertEquals(200, response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable t) {
                assertEquals(null, t);
            }
        });
    }
}