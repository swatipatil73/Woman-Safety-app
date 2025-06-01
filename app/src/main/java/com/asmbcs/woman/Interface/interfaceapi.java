package com.asmbcs.woman.Interface;



import com.asmbcs.woman.modelclass.LoginRequest;
import com.asmbcs.woman.modelclass.LoginResponse;
import com.asmbcs.woman.modelclass.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface interfaceapi {





    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);



   // @POST("registration/")
   // Call<RegistrationResponse> registerUser(@Body RegistrationRequest registrationRequest);
   @POST("registration/")
   Call<User> registerUser(@Body User user);




}




