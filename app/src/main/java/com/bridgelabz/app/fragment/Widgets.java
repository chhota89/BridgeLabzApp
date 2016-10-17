package com.bridgelabz.app.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.CustomActivity;
import com.bridgelabz.app.activity.ExoPlayer;
import com.bridgelabz.app.activity.MainActivity;
import com.bridgelabz.app.activity.QuizActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bridgelabz5 on 26/5/16.
 */
public class Widgets extends Fragment {

    private static final int ASK_READ_WRITE_PERMISSION = 236;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.widget, container, false);

        Button dateButton, timeButton,customView;
        dateButton = (Button) view.findViewById(R.id.dateButton);
        timeButton = (Button) view.findViewById(R.id.timeButton);
        customView =(Button) view.findViewById(R.id.customView);
        final TextView date = (TextView) view.findViewById(R.id.date);
        final TextView time = (TextView) view.findViewById(R.id.time);
        //Set On click listener for DateButton Click
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Date picker dialog.
                new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                    //When  date is set.
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText("" + dayOfMonth + " / " + monthOfYear + " / " + year);
                    }
                }, 2015, 10, 10).show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open Time picker dialog.
                new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText("" + hourOfDay + " : " + minute);
                    }
                }, 11, 56, true).show();


            }
        });

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),CustomActivity.class));
            }
        });

        Button capture = (Button) view.findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23)
                    takeRunTimePermissionForStorage(ASK_READ_WRITE_PERMISSION);
                else
                    captureScreen();
            }
        });


        Button exoplayer=(Button)view.findViewById(R.id.exoplayer);
        exoplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ExoPlayer.class));
            }
        });

        Button quiz=(Button)view.findViewById(R.id.quiz);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), QuizActivity.class));
            }
        });
        return view;
    }


    private void takeRunTimePermissionForStorage(int work) {
        if(Build.VERSION.SDK_INT>=23){
            //check for ACCESS_COARSE_LOCATION permission
            int hasPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

            if (work ==ASK_READ_WRITE_PERMISSION && hasPermission == PackageManager.PERMISSION_GRANTED && readPermission==PackageManager.PERMISSION_GRANTED) {
                captureScreen();
                return;
            }

            requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    work);
        }
    }

    private void captureScreen() {
        String mPath = Environment.getExternalStorageDirectory().toString() + "/fileName" + ".jpg";
        View v1 = getActivity().getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);

        File imageFile = new File(mPath);
        if(!imageFile.exists()) {
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageFile.mkdir();
            imageFile.mkdirs();
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(mPath), "image/*");
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ASK_READ_WRITE_PERMISSION:
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    captureScreen();
                }
        }
    }
}
