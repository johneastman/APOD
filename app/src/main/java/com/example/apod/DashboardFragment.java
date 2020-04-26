package com.example.apod;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class DashboardFragment extends Fragment {

    private ImageButton imageButtonPreviousAPOD;
    private ImageButton imageButtonNextAPOD;

    private TextView textViewDate;

    private APODDate apodDate = new APODDate();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment_layout, null); //container, false);

        imageButtonPreviousAPOD = view.findViewById(R.id.previous_apod);
        imageButtonPreviousAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apodDate.subtractDay();
                textViewDate.setText(apodDate.formatted());
                //getAPODFragment().onResponse(apodDate.formatted());
            }
        });

        imageButtonNextAPOD = view.findViewById(R.id.next_apod);
        imageButtonNextAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apodDate.addDay();
                textViewDate.setText(apodDate.formatted());
                //getAPODFragment().onResponse(apodDate.formatted());
            }
        });

        textViewDate = view.findViewById(R.id.apod_date);
        textViewDate.setText(apodDate.formatted());

        return view;
    }

    private void pickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                apodDate.setDate(year, month, dayOfMonth);
                //getAPODFragment().onResponse(apodDate.formatted());
            }
        }, apodDate.getYear(), apodDate.getMonth() - 1, apodDate.getDayOfMonth());
        datePickerDialog.getDatePicker().setMaxDate(apodDate.getMaxDate());
        datePickerDialog.getDatePicker().setMinDate(apodDate.getMinDate());
        datePickerDialog.show();
    }

    private APODFragment getAPODFragment() {
        FragmentManager fm = getFragmentManager();
        return (APODFragment) fm.findFragmentById(R.id.apod_fragment_container);
    }
}
