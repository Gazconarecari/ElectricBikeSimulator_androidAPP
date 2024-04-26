package com.example.P3ModelMaterialViewListSubActivity;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.example.P3ModelMaterialViewListSubActivity.History;

import androidx.annotation.NonNull;

import com.example.P3ModelMaterialViewListSubActivity.Models.InterestPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class
TravelPointsApplication extends Application {

    private List<InterestPoint> pointList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(InterestPoint.class);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }

    public List<InterestPoint> getPoints() {
        return pointList;
    }

    public InterestPoint getPointByIndex(int i) {
        return pointList.get(i);
    }

    public void addPoint(InterestPoint point) {
        pointList.add(point);
    }

    public void clear() {
        pointList.clear();
    }

    public void addObjectUpdate(@NonNull InterestPoint aInterestPoint) {
        aInterestPoint.saveInBackground(e -> {
            if (e == null) {
                Log.d("object saved server OK:", "addObjectUpdate()");
            } else {
                Log.d("fail save, reason: ", e.getMessage());
            }
        });
    }
    public void updateObjectUpdate(@NonNull InterestPoint aInterestPoint) {
        aInterestPoint.saveInBackground(e -> {
            if (e == null) {
                Log.d("object updated srv OK:", "updateObjectUpdate()");
            } else {
                Log.d("fail update, reason: ", e.getMessage());
            }
        });
    }


}

