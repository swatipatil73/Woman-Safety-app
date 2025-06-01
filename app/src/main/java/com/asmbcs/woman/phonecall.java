package com.asmbcs.woman;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asmbcs.woman.adapter.EmergencyAdapter;
import com.asmbcs.woman.modelclass.EmergencyContact;

import java.util.ArrayList;
import java.util.List;

public class phonecall extends AppCompatActivity {
    private static final int CALL_PERMISSION_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phonecall);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Check Call Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST);
        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 items per row

        List<EmergencyContact> contacts = new ArrayList<>();
        contacts.add(new EmergencyContact("Police", R.drawable.p, "100"));
        contacts.add(new EmergencyContact("Ambulance", R.drawable.ambulance, "108"));
        contacts.add(new EmergencyContact("Hospital", R.drawable.h, "102"));
        ;
        contacts.add(new EmergencyContact("Fire Brigade", R.drawable.f, "101"));
        contacts.add(new EmergencyContact("Women Helpline", R.drawable.women_helpline, "1091"));
        contacts.add(new EmergencyContact("Child Helpline", R.drawable.c, "1098"));

        EmergencyAdapter adapter = new EmergencyAdapter(this, contacts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish(); // Close app if permission is denied
            }
        }
    }
}