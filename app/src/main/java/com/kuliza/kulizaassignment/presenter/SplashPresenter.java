package com.kuliza.kulizaassignment.presenter;

/**
 * SplashPresenter Interface
 */
public interface SplashPresenter {

    void onResume();

    void onDestroy();

    void loadForecastData(String key, String q, String days);
}
