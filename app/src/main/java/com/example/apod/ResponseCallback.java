package com.example.apod;

public interface ResponseCallback {

    void onComplete(APOD apod, String errorMessage);
}
