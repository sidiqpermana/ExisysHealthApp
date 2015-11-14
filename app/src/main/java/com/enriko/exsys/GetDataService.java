package com.enriko.exsys;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.enriko.exsys.base.CustomApplication;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sidiq on 06/09/2015.
 */
public class GetDataService extends Service implements Observer{

    CustomApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = (CustomApplication)getApplication();
        application.getBloodPressureObserver().addObserver(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new ReceivedDelayAsync().execute();
        return START_STICKY;
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    private class ReceivedDelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(4000);
            }catch (Exception e){}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            application.getBloodPressureObserver().getData("done");
            stopSelf();
        }
    }
}
