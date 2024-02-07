package com.example.kstoredemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button startButton,quitButton;
    TextView Time, Datet;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quitButton = findViewById(R.id.btnQuit);
        Time = findViewById(R.id.timetext);
        Datet = findViewById(R.id.datetext);
        String dateTime, time;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat, simpleDateFormat1;
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        dateTime = simpleDateFormat.format(calendar.getTime());
        Datet.setText(dateTime);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar calendar1 = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm:ss");
                                String currenttime = simpleDateFormat2.format(calendar1.getTime());
                                Time.setText(currenttime);
                            }
                        });
                    }
                } catch (Exception e) {
                    Time.setText(R.string.app_name);
                }
            }
        };
        thread.start();
        startButton = findViewById(R.id.btnstart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isInternetAvailable(MainActivity.this)) {
                    Intent intent = new Intent(MainActivity.this, Services.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    showNoInternetDialog();
                    System.out.println("No internet");
                }
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setIcon(R.drawable.keralaapplogo)
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Confirm Exit!")
                .setMessage("Do You Want To Close Application?")
                .setCancelable(false)
                .setIcon(R.drawable.keralaapplogo)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }
}