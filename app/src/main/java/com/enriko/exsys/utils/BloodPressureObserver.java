package com.enriko.exsys.utils;

import java.util.Observable;

/**
 * Created by Sidiq on 15/08/2015.
 */
public class BloodPressureObserver extends Observable {
    public void getData(String data){
        setChanged();
        notifyObservers(data);
    }
}
