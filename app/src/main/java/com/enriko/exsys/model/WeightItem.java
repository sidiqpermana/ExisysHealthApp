package com.enriko.exsys.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sidiq on 08/06/2015.
 */
public class WeightItem implements Serializable {
    @SerializedName("weight")
    public int weight;

    @SerializedName("tanggal")
    public String tanggal;
}
