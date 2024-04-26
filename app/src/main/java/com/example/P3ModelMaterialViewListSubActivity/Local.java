package com.example.P3ModelMaterialViewListSubActivity;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.P3ModelMaterialViewListSubActivity.Models.InterestPoint;
import com.example.P3ModelMaterialViewListSubActivity.databinding.ActivityLocalBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Local extends AppCompatActivity {

    private ActivityLocalBinding binding;
    TravelPointsApplication tpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        tpa = (TravelPointsApplication) getApplicationContext();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_local);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_local) {
                return true;
            } else if (itemId == R.id.navigation_history) {
                startActivity(new Intent(getApplicationContext(), History.class));
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
                return true;
            }else if (itemId== R.id.navigation_sim){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                finish();
                return true;
            }
            return false;
        });

        Intent intent = getIntent();
        if (intent != null) {
            updateUIWithIntentData(intent);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void updateUIWithIntentData(Intent intent) {
        int id = intent.getIntExtra("id", 0);
        double latitude = intent.getDoubleExtra("latitude", 0.0);
        double longitude = intent.getDoubleExtra("longitude", 0.0);
        int parkingN = intent.getIntExtra("parkingN", 0);
        String parking = intent.getStringExtra("parking");
        int battery = intent.getIntExtra("battery", 0);
        float remainingTime = intent.getFloatExtra("remainingTime", 0);
        float temperature = intent.getFloatExtra("temperature", 0);
        boolean chargerConnected = intent.getBooleanExtra("chargerConnected", false);
        float pressure = intent.getFloatExtra("pressure", 0);
        boolean inflationRecommendation = intent.getBooleanExtra("inflationRecommendation", false);
        String repair = intent.getStringExtra("repair");

        parking = parking != null ? parking : "N/A";
        repair = repair != null ? repair : "N/A";

        String photoPath = intent.getStringExtra("photopath");
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            ImageView imageView = findViewById(R.id.image1);
            imageView.setImageBitmap(bitmap);
        }
        ((TextView) findViewById(R.id.showID)).setText(String.format("%d", id));
        ((TextView) findViewById(R.id.showLatitude)).setText(String.format("%.5f", latitude));
        ((TextView) findViewById(R.id.showLongitude)).setText(String.format("%.5f", longitude));
        ((TextView) findViewById(R.id.showParkingN)).setText(String.format("%d", parkingN));
        ((TextView) findViewById(R.id.showParking)).setText(String.format("%s", parking));
        ((TextView) findViewById(R.id.showBattery)).setText(String.format("%d%%", battery));
        ((TextView) findViewById(R.id.showTime)).setText(String.format("%.2f", remainingTime));
        ((TextView) findViewById(R.id.showTemp)).setText(String.format("%.2f°C", temperature));
        ((TextView) findViewById(R.id.showCharger)).setText(String.format("%s", chargerConnected ? "Yes" : "No"));
        ((TextView) findViewById(R.id.showPressure)).setText(String.format("%.2f", pressure));
        ((TextView) findViewById(R.id.showInflation)).setText(String.format("%s", inflationRecommendation ? "Yes" : "No"));
        //((TextView) findViewById(R.id.showDate)).setText(String.format("%s", date));
        ((TextView) findViewById(R.id.showRepair)).setText(String.format("%s", repair));

        if (!"N/A".equals(repair)) {
            InterestPoint point = new InterestPoint();
            point.setLatitude(latitude);
            point.setLongitude(longitude);
            point.setParkingN(parkingN);
            point.setParking(parking);
            point.setBattery(battery);
            point.setRemainingTime(remainingTime);
            point.setTemperature(temperature);
            point.setChargerConnected(chargerConnected);
            point.setPressure(pressure);
            point.setInflationRecommendation(inflationRecommendation);
            point.setRepair(repair);

            tpa.addObjectUpdate(point);
        }
    }

}