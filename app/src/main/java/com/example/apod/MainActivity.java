package com.example.apod;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        /*
        Fragment dashboardFragment = fm.findFragmentById(R.id.dashboard_fragment_container);
        if (dashboardFragment == null) {
            dashboardFragment = new DashboardFragment();
            transaction.add(R.id.apod_fragment_container, dashboardFragment);
        }
        */

        Fragment APODFragment = fm.findFragmentById(R.id.apod_fragment_container);
        if (APODFragment == null) {
            APODFragment = new APODFragment();
            transaction.add(R.id.apod_fragment_container, APODFragment);
        }

        transaction.commit();
    }
}
