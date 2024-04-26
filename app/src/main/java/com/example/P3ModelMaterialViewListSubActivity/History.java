package com.example.P3ModelMaterialViewListSubActivity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.P3ModelMaterialViewListSubActivity.Models.InterestPoint;
import com.example.P3ModelMaterialViewListSubActivity.databinding.ActivityHistoryBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class History extends AppCompatActivity {


    private ActivityHistoryBinding binding;
    TravelPointsApplication tpa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        tpa = (TravelPointsApplication) getApplicationContext();
        fetchLastPoints();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_history);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_history) {
                return true;
            } else if (itemId == R.id.navigation_sim) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
                return true;
            } else if (itemId== R.id.navigation_local){
                startActivity(new Intent(getApplicationContext(), Local.class));
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
                return true;
            }
            return false;
        });

    }
    public void fetchLastPoints() {
        ParseQuery<InterestPoint> query = ParseQuery.getQuery("InterestPoint");
        query.orderByDescending("createdAt");

        query.findInBackground((objects, e) -> {
            if (e == null) {
                if (!objects.isEmpty()) {
                    updateCharts(objects);
                    updateListView(objects);
                } else {
                    Log.d("Data Fetch", "No Points Found");
                }
            } else {
                Log.d("Data Fetch Error", "Error: " + e.getMessage());
            }
        });
    }
    public void updateCharts(List<InterestPoint> points) {
        List<Entry> temperatureEntries = new ArrayList<>();
        List<Entry> pressureEntries = new ArrayList<>();
        List<Entry> batteryEntries = new ArrayList<>();

        Collections.reverse(points);

        for (int i = 0; i < points.size(); i++) {
            InterestPoint point = points.get(i);

            Log.d("Chart Data", "Index: " + i + " Temperature: " + point.getTemperature());
            Log.d("Chart Data", "Index: " + i + " Pressure: " + point.getPressure());
            temperatureEntries.add(new Entry(i, point.getTemperature()));
            pressureEntries.add(new Entry(i, point.getPressure()));
            if (point.getBattery() != null)
            {
                batteryEntries.add(new Entry(i, point.getBattery()));
            }

        }

        LineDataSet tempDataSet = new LineDataSet(temperatureEntries, "Temperature");
        LineDataSet pressDataSet = new LineDataSet(pressureEntries, "Pressure");

        LineChart tempChart = findViewById(R.id.chart_temperature);
        LineChart pressChart = findViewById(R.id.chart_pressure);

        tempChart.setData(new LineData(tempDataSet));
        pressChart.setData(new LineData(pressDataSet));

        tempChart.invalidate();
        pressChart.invalidate();



    }

    public void updateListView(List<InterestPoint> points) {
        List<String> creationDates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        for (InterestPoint point : points) {
            Date createdAt = point.getCreatedAt();
            String repair = point.getRepair() != null ? point.getRepair() : "No repair info";
            String formattedDate = dateFormat.format(createdAt);
            String listItemText = createdAt + ": " + repair;
            creationDates.add(listItemText);
        }

        ListView listView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, creationDates);
        listView.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}