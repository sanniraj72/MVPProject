package com.kuliza.kulizaassignment.view;

/**
 * SplashView interface to handle the view related changes
 */
public interface SplashView {
    void showProgress();

    void hideProgress();

    void navigateToHome();

    void showErrorMessage(String errorMessage);
}
