package com.example.P3ModelMaterialViewListSubActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.P3ModelMaterialViewListSubActivity.Models.InterestPoint;
import com.example.P3ModelMaterialViewListSubActivity.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int SUBACTIVITY_ADD = 0;
    private static final int SUBACTIVITY_MODIFY = 1;
    static final int REQUEST_CAMERA_PERMISSION = 101;
    static final int REQUEST_IMAGE_CAPTURE = 100;


    private static final int SEND_FROM_SIMULATION_TO_LOCAL = 10;
    String photoPath;
    private ActivityMainBinding binding;

    ImageView imageView;
    private static final int REQUEST_CODE = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Button btnCamera = findViewById(R.id.button_open_camera);
        imageView = findViewById(R.id.image);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePictureIntent, REQUEST_CODE);
                }


            }
        });

        SeekBar temperatureEdit = findViewById(R.id.temperatureEdit);
        SeekBar pressureEdit = findViewById(R.id.pressureEdit);
        SeekBar timeEdit = findViewById(R.id.timeEdit);
        SeekBar batteryEdit = findViewById(R.id.batteryEdit);

        // Inicializar los TextViews para mostrar el progreso
        TextView showPTemp = findViewById(R.id.showPTemp);
        TextView showPPres = findViewById(R.id.showPPres);
        TextView showPTime = findViewById(R.id.showPTime);
        TextView showPBat = findViewById(R.id.showPBat);

        showPTemp.setText(String.valueOf("0"));
        showPPres.setText(String.valueOf("0"));
        showPTime.setText(String.valueOf("0"));
        showPBat.setText(String.valueOf("0"));


        // Listener para Temperature SeekBar
        temperatureEdit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showPTemp.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Listener para Pressure SeekBar
        pressureEdit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showPPres.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Listener para Time SeekBar
        timeEdit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showPTime.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Listener para Battery SeekBar
        batteryEdit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                showPBat.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_sim);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_sim) {
                return true;
            } else if (itemId == R.id.navigation_history) {
                startActivity(new Intent(getApplicationContext(), History.class));
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
                return true;
            }else if (itemId== R.id.navigation_local){
                startActivity(new Intent(getApplicationContext(), Local.class));
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                finish();
                return true;
            }
            return false;
        });

        Button simulateButton = findViewById(R.id.simulateButton);
        simulateButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Local.class);
            EditText idEdit = findViewById(R.id.idEdit);
            EditText latitudeEdit = findViewById(R.id.latitudeEdit);
            EditText longitudeEdit = findViewById(R.id.longitudeEdit);
            EditText parkingnEdit = findViewById(R.id.parkingnEdit);
            EditText parkingEdit = findViewById(R.id.parkingEdit);
            Switch chargerEdit = findViewById(R.id.chargerEdit);
            Switch inflationEdit = findViewById(R.id.inflationEdit);
            EditText repairEdit = findViewById(R.id.repairEdit);


            int id = Integer.parseInt(idEdit.getText().toString());
            double latitude = Double.parseDouble(latitudeEdit.getText().toString());
            double longitude = Double.parseDouble(longitudeEdit.getText().toString());
            int parkingN = Integer.parseInt(parkingnEdit.getText().toString());
            String parking = parkingEdit.getText().toString();
            int battery = batteryEdit.getProgress();
            float remainingTime = (float)timeEdit.getProgress();
            float temperature = (float) temperatureEdit.getProgress();
            boolean chargerConnected = chargerEdit.isChecked();
            float pressure = (float) pressureEdit.getProgress();
            boolean inflationRecommendation = inflationEdit.isChecked();
            String repair = repairEdit.getText().toString();



// Create and configure the intent

            intent.putExtra("id", id);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            intent.putExtra("parkingN", parkingN);
            intent.putExtra("parking", parking);
            intent.putExtra("battery", battery);
            intent.putExtra("remainingTime", remainingTime);
            intent.putExtra("temperature", temperature);
            intent.putExtra("chargerConnected", chargerConnected);
            intent.putExtra("pressure", pressure);
            intent.putExtra("inflationRecommendation", inflationRecommendation);
            intent.putExtra("repair", repair);
            intent.putExtra("photopath", photoPath);

// Log each value
            Log.d("IntentData", "ID: " + id);
            Log.d("IntentData", "Latitude: " + latitude);
            Log.d("IntentData", "Longitude: " + longitude);
            Log.d("IntentData", "Parking Number: " + parkingN);
            Log.d("IntentData", "Parking: " + parking);
            Log.d("IntentData", "Battery: " + battery);
            Log.d("IntentData", "Remaining Time: " + remainingTime);
            Log.d("IntentData", "Temperature: " + temperature);
            Log.d("IntentData", "Charger Connected: " + chargerConnected);
            Log.d("IntentData", "Pressure: " + pressure);
            Log.d("IntentData", "Inflation Recommendation: " + inflationRecommendation);
            Log.d("IntentData", "Repair: " + repair);

            startActivityForResult(intent, SEND_FROM_SIMULATION_TO_LOCAL); // Make sure REQUEST_CODE is defined as an int
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);

            String photoPath = saveImage(photo);
            if (photoPath != null) {
                this.photoPath = photoPath;
            }

        }
    }

    private String saveImage(Bitmap bitmap) {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = "photo_" + System.currentTimeMillis() + ".jpg";
        File imageFile = new File(storageDir, fileName);
        try (FileOutputStream out = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return imageFile.getAbsolutePath();
    }





}