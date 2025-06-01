package com.asmbcs.woman;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.asmbcs.woman.Interface.ApiClient;
import com.asmbcs.woman.Interface.interfaceapi;
import com.asmbcs.woman.modelclass.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registration extends AppCompatActivity {
    private EditText etFirstName, etLastName, etMobile, etBirthdate, etMarriedStatus, etRole, etAddress, etEmail, etUsername, etPassword;
    private Button btnRegister;

    private interfaceapi apiService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize API Service
        apiService = ApiClient.getClient().create(interfaceapi.class);

        // Initialize UI components
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etMobile = findViewById(R.id.etMobile);
        etBirthdate = findViewById(R.id.etBirthdate);
        etMarriedStatus = findViewById(R.id.etMarriedStatus);
        etRole = findViewById(R.id.etRole);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        etRole.setText("Woman");
        // Set button click listener
        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String mobile = etMobile.getText().toString();
        String birthdate = etBirthdate.getText().toString();
        String marriedStatus = etMarriedStatus.getText().toString();
        String role = etRole.getText().toString();
        String address = etAddress.getText().toString();
        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        User user = new User(firstName, lastName, mobile, birthdate, marriedStatus, role, address, email, username, password);

        apiService.registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(registration.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(registration.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(registration.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

