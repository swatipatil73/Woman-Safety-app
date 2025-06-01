package com.asmbcs.woman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.asmbcs.woman.Interface.interfaceapi;
import com.asmbcs.woman.modelclass.LoginRequest;
import com.asmbcs.woman.modelclass.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView registerLink;
    interfaceapi apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activityg_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.registerLink);
      //  ImageView imageView = findViewById(R.id.loging);
        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.128:8000/")  // Replace with your actual base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(interfaceapi.class);


        // Login button click listener
        btnLogin.setOnClickListener(view -> loginUser());

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, registration.class);
                startActivity(intent);
                Toast.makeText(login.this, "woman safety + location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    saveTokens(loginResponse.getAccessToken(), loginResponse.getRefreshToken());

                    SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    Toast.makeText(login.this, "Successfully Logged In ✅", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log the full request URL
                String requestUrl = call.request().url().toString();
                Log.e("API_ERROR", "API call failed: " + t.getMessage());
                Log.e("API_ERROR", "Request URL: " + requestUrl);

                // Show Toast message
                Toast.makeText(login.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
            });
    }

    // Method to refresh the access token using the refresh token


    // Method to save access and refresh tokens in SharedPreferences
    private void saveTokens(String accessToken, String refreshToken) {
        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
        editor.putString("access_token", accessToken);
        editor.putString("refresh_token", refreshToken);
        editor.apply();
    }

    // Method to retrieve the access token from SharedPreferences
    public String getAccessToken() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return prefs.getString("access_token", null);
    }

    // Method to retrieve the refresh token from SharedPreferences
    public String getRefreshToken() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return prefs.getString("refresh_token", null);
    }
}