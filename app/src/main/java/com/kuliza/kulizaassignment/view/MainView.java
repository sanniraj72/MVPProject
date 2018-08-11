package com.kuliza.kulizaassignment.view;

import com.kuliza.kulizaassignment.model.ForecastResponse;

/**
 * SplashView interface to handle the view related changes
 */
public interface MainView {
    void showData(ForecastResponse forecastResponse);
}
