package com.enriko.exsys.library;

import android.util.Log;

public class PrintBytes {
    public PrintBytes() {
    }

    public static void printData(byte[] pack) {
        Log.i("***********************", "************************");
        String _temp = "";

        for(int i = 0; i < pack.length; ++i) {
            if(i >= 3 && (i - 2) % 7 == 1) {
                Log.i("Data", _temp);
                _temp = "";
            }

            _temp = _temp + " " + Integer.toHexString(pack[i]);
        }

        Log.i("Data", _temp);
        Log.i("***********************", "************************");
    }

    public static void printData(byte[] pack, int count) {
        String _temp = "";

        for(int i = 0; i < count; ++i) {
            _temp = _temp + " " + Integer.toHexString(pack[i] & 255);
        }

        Log.i("Data", _temp);
    }
}
