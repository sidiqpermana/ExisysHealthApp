package com.enriko.exsys.base;

import android.app.Application;

import com.enriko.exsys.utils.BloodPressureObserver;

/**
 * Created by Sidiq on 15/08/2015.
 */
public class CustomApplication extends Application {
    BloodPressureObserver bloodPressureObserver;
    @Override
    public void onCreate() {
        super.onCreate();
        bloodPressureObserver = new BloodPressureObserver();
    }

    public BloodPressureObserver getBloodPressureObserver(){
        return bloodPressureObserver;
    }
}
