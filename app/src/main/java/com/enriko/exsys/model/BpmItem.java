package com.enriko.exsys.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sidiq on 08/06/2015.
 */
public class BpmItem implements Serializable {

    @SerializedName("sistolik")
    public int sistolik;

    @SerializedName("diastolik")
    public int diastolik;

    @SerializedName("tanggal")
    public String tanggal;
}
