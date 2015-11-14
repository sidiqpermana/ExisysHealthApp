package com.enriko.exsys.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sidiq on 07/06/2015.
 */
public class HrmItem implements Serializable {
    @SerializedName("hrm")
    public int hrm;

    @SerializedName("tanggal")
    public String tanggal;
}

