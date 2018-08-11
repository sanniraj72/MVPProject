package com.kuliza.kulizaassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kuliza.kulizaassignment.KulizaApplication;
import com.kuliza.kulizaassignment.R;
import com.kuliza.kulizaassignment.presenter.SplashPresenter;
import com.kuliza.kulizaassignment.presenter.SplashPresenterImpl;
import com.kuliza.kulizaassignment.util.CDef;

public class SplashActivity extends AppCompatActivity implements SplashView, View.OnClickListener {

    // Loading layout with progress bar
    private LinearLayout loadingLayout;
    // Error layout
    private LinearLayout errorLayout;

    // Button to retry forecast data
    private Button retryButton;

    // SplashPresenter instance
    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        setContentView(R.layout.activity_splash);
        init();
        splashPresenter = new SplashPresenterImpl(this);
    }

    /**
     * Initialize View
     */
    private void init() {
        loadingLayout = findViewById(R.id.loading_layout);
        errorLayout = findViewById(R.id.error_layout);
        retryButton = findViewById(R.id.retry_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        retryButton.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        retryButton.setOnClickListener(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        splashPresenter.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashPresenter.onResume();
    }

    @Override
    public void showProgress() {
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {
        // Launch Main Activity
        Log.d(KulizaApplication.TAG, "Launching MainActivity...");
        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
        // mainIntent.putExtra(CDef.FORECAST.getValue(), forecastResponse);
        startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry_button:
                errorLayout.setVisibility(View.GONE);
                loadingLayout.setVisibility(View.VISIBLE);
                splashPresenter.loadForecastData(CDef.API_KEY.getValue(), CDef.PLACE.getValue(), CDef.DAYS.getValue());
                break;
            default:
                break;
        }
    }
}
