package com.kuliza.kulizaassignment.presenter;

import com.kuliza.kulizaassignment.db.RealmDB;
import com.kuliza.kulizaassignment.model.ForecastResponse;
import com.kuliza.kulizaassignment.view.MainView;

/**
 * MainPresenterImpl - Presenter Implementation
 */
public class MainPresenterImpl implements MainPresenter {

    // MainView Instance variable
    private MainView mainView;

    /**
     * Constructor
     *
     * @param mainView mainView
     */
    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void loadData() {
        // Load data from Realm
        ForecastResponse forecastResponse = RealmDB.ForecastRealm.find();
        mainView.showData(forecastResponse);
    }
}
