package com.example.apod;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class APODFragment extends Fragment {

    private TextView textViewTitle;
    private TextView textViewCopyright;
    private TextView textViewDescription;
    private TextView textViewDate;
    private ImageView imageViewAPODImage;

    private ImageButton imageButtonPreviousAPOD;
    private ImageButton imageButtonNextAPOD;

    //private ImageView loadingImage;

    private APODDate apodDate = new APODDate();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apod_fragment_layout, container, false);

        setHasOptionsMenu(true);

        textViewTitle = view.findViewById(R.id.apod_title);
        textViewCopyright = view.findViewById(R.id.apod_copyright);
        textViewDescription = view.findViewById(R.id.apod_description);
        textViewDate = view.findViewById(R.id.apod_date);
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        imageViewAPODImage = view.findViewById(R.id.apod_image);

        imageButtonPreviousAPOD = view.findViewById(R.id.previous_apod);
        imageButtonPreviousAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apodDate.subtractDay();
                textViewDate.setText(apodDate.formatted());
                onResponse(apodDate.formatted());
            }
        });

        imageButtonNextAPOD = view.findViewById(R.id.next_apod);
        imageButtonNextAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apodDate.addDay();
                textViewDate.setText(apodDate.formatted());
                onResponse(apodDate.formatted());
            }
        });

        //loadingImage = view.findViewById(R.id.loading_image);

        onResponse(null);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.raw_response:
                Intent intent = new Intent(getActivity().getBaseContext(), RawResponseActivity.class);
                intent.putExtra(RawResponseActivity.EXTRA_RAW_RESPONSE, APOD.getInstance().getRawResponse());
                startActivity(intent);
                return true;
            case R.id.apod_datepicker:
                pickDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onResponse(String date) {
        //loadingImage.setVisibility(View.VISIBLE);
        textViewDate.setText(apodDate.formatted());

        APODRequest apodRequest = new APODRequest(getContext(), "U49pcyFPwCOyQpCFTIlJf9SEeJgjIpGhXshmlUmf");
        apodRequest.get(date, new ResponseCallback() {
            @Override
            public void onComplete(APOD apod, String errorMessage) {

                // Disable Progress Bar
                //loadingImage.setVisibility(View.GONE);

                if (errorMessage == null) {
                    // No error
                    textViewTitle.setText(apod.getTitle());
                    textViewDescription.setText(apod.getDescription());

                    if (apod.getMediaType().equals("image")) {
                        imageViewAPODImage.setVisibility(View.VISIBLE);
                        imageViewAPODImage.setImageBitmap(apod.getImage());
                    } else {
                        imageViewAPODImage.setVisibility(View.GONE);
                    }

                    imageViewAPODImage.setImageBitmap(apod.getImage());
                    textViewCopyright.setText(apod.getCopyright());
                } else {
                    // Display Error
                    Dialog dialog = getErrorAlertDialog(errorMessage);
                    dialog.show();
                }
            }
        });
    }

    private Dialog getErrorAlertDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("An Error Occurred");
        builder.setMessage(errorMessage);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    private void pickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                apodDate.setDate(year, month, dayOfMonth);
                onResponse(apodDate.formatted());
            }
        }, apodDate.getYear(), apodDate.getMonth() - 1, apodDate.getDayOfMonth());
        datePickerDialog.getDatePicker().setMaxDate(apodDate.getMaxDate());
        datePickerDialog.getDatePicker().setMinDate(apodDate.getMinDate());
        datePickerDialog.show();
    }
}
