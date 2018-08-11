package com.kuliza.kulizaassignment;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * KulizaApplication - Application Class
 */
public class KulizaApplication extends Application {

    // Base URL for API
    private static final String BASE_URL = "http://api.apixu.com/v1/";

    // Retrofit Instance
    private Retrofit retrofit;

    // KulizaApplication Instance
    private static KulizaApplication kulizaApplication;

    // Application TAG
    public static final String TAG = "kuliza-app";

    /**
     * Get Instance of KulizaApplication
     *
     * @return KulizaApplication Instance
     */
    public synchronized static KulizaApplication getInstance() {
        if (kulizaApplication == null) {
            kulizaApplication = new KulizaApplication();
        }
        return kulizaApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        kulizaApplication = this;
        // Initialize Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * Get Instance of Retrofit
     *
     * @return retrofit instance
     */
    public Retrofit getRetrofit() {
        if (retrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
