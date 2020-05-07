package com.kotalishious.bwblackmarket.Fragments;

import com.kotalishious.bwblackmarket.Activities.Notifications.MyResponse;
import com.kotalishious.bwblackmarket.Activities.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAs3dBd8A:APA91bGsfiKxbLiEiC5FgKYZECgcJY1FRJKoaQC8XRL_4s9ByDtnUOrm9-XIkNN7-YEcu1LVZpMNcwXiA9E4PpwM51fz054PLh5i9sEjGEWTn8YraMpZWkgFqsR9I4Ca_R4-AQj2yEdk"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
