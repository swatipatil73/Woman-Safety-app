package com.asmbcs.woman;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ContactActivity extends AppCompatActivity {

    private EditText contact1, contact2, contact3;
    private Button saveButton;
    private int shakeCount = 0;
    private SensorManager sensorManager;
    private float accel;  // Acceleration without gravity
    private float accelCurrent;  // Current acceleration including gravity
    private float accelLast;  // Last acceleration value
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        contact1 = findViewById(R.id.contact1);
        contact2 = findViewById(R.id.contact2);
        contact3 = findViewById(R.id.contact3);
        saveButton = findViewById(R.id.saveButton);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Load saved contacts
        SharedPreferences sharedPreferences = getSharedPreferences("EmergencyContacts", MODE_PRIVATE);
        contact1.setText(sharedPreferences.getString("contact1", ""));
        contact2.setText(sharedPreferences.getString("contact2", ""));
        contact3.setText(sharedPreferences.getString("contact3", ""));

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("contact1", contact1.getText().toString());
            editor.putString("contact2", contact2.getText().toString());
            editor.putString("contact3", contact3.getText().toString());
            editor.apply();
            Toast.makeText(ContactActivity.this, "Contacts saved successfully!", Toast.LENGTH_SHORT).show();
        });

        // Initialize SensorManager for shake detection
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            sensorManager.registerListener(sensorListener,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                accelLast = accelCurrent;
                accelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
                float delta = accelCurrent - accelLast;
                accel = accel * 0.9f + delta; // Apply low-pass filter

                if (accel > 12) { // Shake threshold
                    shakeCount++;

                    Log.d("Shake Detection", "Shake detected! Count: " + shakeCount);

                    if (shakeCount >= 3) {
                        sendEmergencyMessage();
                        shakeCount = 0; // Reset count
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void sendEmergencyMessage() {
        Toast.makeText(this, "location sent", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    String locationUrl = "https://www.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude();
                    SharedPreferences sharedPreferences = getSharedPreferences("EmergencyContacts", MODE_PRIVATE);
                    String contact1 = sharedPreferences.getString("contact1", "");
                    String contact2 = sharedPreferences.getString("contact2", "");
                    String contact3 = sharedPreferences.getString("contact3", "");

                    SmsManager smsManager = SmsManager.getDefault();
                    String message = "Emergency! I'm at: " + locationUrl;

                    if (!contact1.isEmpty()) smsManager.sendTextMessage(contact1, null, message, null, null);
                    if (!contact2.isEmpty()) smsManager.sendTextMessage(contact2, null, message, null, null);
                    if (!contact3.isEmpty()) smsManager.sendTextMessage(contact3, null, message, null, null);

                    Toast.makeText(ContactActivity.this, "Emergency messages sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ContactActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }
}