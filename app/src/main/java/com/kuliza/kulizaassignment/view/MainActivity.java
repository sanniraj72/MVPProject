package com.kuliza.kulizaassignment.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kuliza.kulizaassignment.KulizaApplication;
import com.kuliza.kulizaassignment.R;
import com.kuliza.kulizaassignment.model.ForecastDay;
import com.kuliza.kulizaassignment.model.ForecastResponse;
import com.kuliza.kulizaassignment.presenter.MainPresenter;
import com.kuliza.kulizaassignment.presenter.MainPresenterImpl;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainView {

    // MainPresenter Instance
    private MainPresenter mainPresenter;

    // Temperature view
    private TextView temView;
    // Place view
    private TextView placeView;

    // RecyclerView of forecast
    private RecyclerView forecastListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mainPresenter = new MainPresenterImpl(this);
    }

    /**
     * Init views
     */
    private void init() {
        temView = findViewById(R.id.current_temp_view);
        placeView = findViewById(R.id.place_view);
        forecastListView = findViewById(R.id.forecast_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
        mainPresenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void showData(ForecastResponse forecastResponse) {
        // Show current data
        char degree = 0x00B0;
        String currentTemp = String.valueOf(forecastResponse.getCurrent().getTemp_c()) + degree;
        temView.setText(currentTemp);
        placeView.setText(forecastResponse.getLocation().getName());
        // Show Forecast data
        final ForecastAdapter forecastAdapter = new ForecastAdapter(this, forecastResponse.getForecast().getForecastday());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forecastListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                forecastListView.smoothScrollToPosition(forecastAdapter.getItemCount());
            }
        }, 1000);
        forecastListView.setLayoutManager(layoutManager);
        forecastListView.setAdapter(forecastAdapter);
    }

    /**
     * Adapter for Forecast data
     */
    class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder> {

        // Context
        private Context context;
        // List of ForecastDay
        private List<ForecastDay> forecastDayList;
        // LayoutInflater instance
        private LayoutInflater inflater;
        // SimpleDateFormat
        private SimpleDateFormat destinationDateFormat;
        // SimpleDateFormat
        private SimpleDateFormat sourceDateFormat;

        /**
         * Constructor
         *
         * @param context         context
         * @param forecastDayList forecastDayList
         */
        ForecastAdapter(Context context, List<ForecastDay> forecastDayList) {
            this.context = context;
            this.forecastDayList = forecastDayList;
            inflater = LayoutInflater.from(context);
            this.destinationDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            this.sourceDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        /**
         * Called when RecyclerView needs a new {@link android.support.v7.widget.RecyclerView.ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ForecastHolder, int)
         */
        @NonNull
        @Override
        public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.forecast_item, parent, false);
            return new ForecastHolder(view);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ForecastHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ForecastHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
            // Set Date
            if (position == 0) {
                holder.dateView.setText(context.getResources().getString(R.string.today));
            } else if (position == 1) {
                holder.dateView.setText(context.getResources().getString(R.string.tomorrow));
            } else {
                String date = forecastDayList.get(position).getDate();
                try {
                    Date sourceDate = sourceDateFormat.parse(date);
                    String finalDate = destinationDateFormat.format(sourceDate);
                    holder.dateView.setText(finalDate);
                } catch (ParseException e) {
                    // If error in parsing then set date got from response
                    Log.d(KulizaApplication.TAG, e.getMessage());
                    holder.dateView.setText(forecastDayList.get(position).getDate());
                }
            }
            // Set Icon
            String url = forecastDayList.get(position).getDay().getCondition().getIcon();
            Picasso.get().load("http://" + url.substring(2, url.length())).into(holder.conditionImageView);
            // Set condition message
            holder.conditionTextView.setText(forecastDayList.get(position).getDay().getCondition().getText());
            // Set Temperature
            char degree = 0x00B0;
            String temp = String.valueOf(forecastDayList.get(position).getDay().getMintemp_c()) + degree + "/" + String.valueOf(forecastDayList.get(position).getDay().getMaxtemp_c()) + degree;
            holder.tempView.setText(temp);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return forecastDayList.size();
        }

        /**
         * ForecastHolder Class
         */
        class ForecastHolder extends RecyclerView.ViewHolder {

            TextView dateView;
            ImageView conditionImageView;
            TextView conditionTextView;
            TextView tempView;

            /**
             * Constructor
             *
             * @param view view
             */
            ForecastHolder(View view) {
                super(view);
                dateView = view.findViewById(R.id.date);
                conditionImageView = view.findViewById(R.id.condition_image_view);
                conditionTextView = view.findViewById(R.id.condition_text_view);
                tempView = view.findViewById(R.id.temp_view);
            }
        }
    }
}
