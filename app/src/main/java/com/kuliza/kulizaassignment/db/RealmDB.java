package com.kuliza.kulizaassignment.db;

import com.kuliza.kulizaassignment.model.ForecastResponse;

import io.realm.Realm;

/**
 * RealmDB - Manage data using realm
 */
public class RealmDB {

    /**
     * ForecastRealm Nested Class
     */
    public static class ForecastRealm {

        /**
         * Save or update forecastResponse data
         *
         * @param forecastResponse forecastResponse
         */
        public static void addOrUpdate(ForecastResponse forecastResponse) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(forecastResponse);
            realm.commitTransaction();
        }

        /**
         * Delete ForecastResponse data from realm
         */
        public static void delete() {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.delete(ForecastResponse.class);
            realm.commitTransaction();
        }

        /**
         * Get ForecastResponse data
         *
         * @return ForecastResponse
         */
        public static ForecastResponse find() {
            return Realm.getDefaultInstance().where(ForecastResponse.class).findFirst();
        }
    }
}
