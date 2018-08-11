package com.kuliza.kulizaassignment.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.kuliza.kulizaassignment.KulizaApplication;
import com.kuliza.kulizaassignment.db.RealmDB;
import com.kuliza.kulizaassignment.model.ForecastResponse;
import com.kuliza.kulizaassignment.service.SplashApi;
import com.kuliza.kulizaassignment.util.CDef;
import com.kuliza.kulizaassignment.view.SplashView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SplashPresenterImpl - Presenter Implementation
 */
public class SplashPresenterImpl implements SplashPresenter {

    // SplashView Instance variable
    private SplashView splashView;

    /**
     * Constructor
     *
     * @param splashView splashView
     */
    public SplashPresenterImpl(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void onResume() {
        splashView.showProgress();
        loadForecastData(CDef.API_KEY.getValue(), CDef.PLACE.getValue(), CDef.DAYS.getValue());
    }

    @Override
    public void onDestroy() {
        splashView = null;
    }

    @Override
    public void loadForecastData(String key, String q, String days) {
        SplashApi interactor = KulizaApplication.getInstance().getRetrofit().create(SplashApi.class);
        Call<ForecastResponse> forecastCall = interactor.getForecast(key, q, days);
        forecastCall.enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResponse> call, @NonNull Response<ForecastResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(KulizaApplication.TAG, "Forecast data loaded successfully ...");
                    // Save data to Realm
                    ForecastResponse forecastResponse = response.body();
                    if (forecastResponse != null) {
                        RealmDB.ForecastRealm.delete();
                        RealmDB.ForecastRealm.addOrUpdate(forecastResponse);
                    }
                    splashView.navigateToHome();
                } else {
                    splashView.showErrorMessage(response.message());
                    Toast.makeText(KulizaApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable t) {
                Log.d(KulizaApplication.TAG, t.getMessage());
                Toast.makeText(KulizaApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                splashView.hideProgress();
                splashView.showErrorMessage(t.getMessage());
            }
        });
    }
}
