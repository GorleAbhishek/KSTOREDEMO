package com.example.kstoredemo;

import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AMisc {
    public String DeviceId(){
        String serialNumber = Build.SERIAL;
        String deviceId = serialNumber.substring(11);
        String Date = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        Calendar calendar = Calendar.getInstance();
        String julianday = String.valueOf(calendar.get(Calendar.DAY_OF_YEAR));
        //String rec = julianDateTest();
        String Receipt = deviceId+julianday+Date;
        Log.d("receiptNumber",Receipt);
        return Receipt;
    }
    }

