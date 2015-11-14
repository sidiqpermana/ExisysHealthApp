package com.enriko.exsys.model;

import com.enriko.exsys.base.model.BaseModel;
import com.enriko.exsys.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sidiq on 08/06/2015.
 */
public class Weight extends BaseModel implements Serializable {
    @SerializedName("data")
    public ArrayList<WeightItem> listItem = new ArrayList<>();

    public static ArrayList<WeightItem> getListItem(String response){
        ArrayList<WeightItem> listItem = null;
        Gson gson = new Gson();
        Weight weight = gson.fromJson(response, Weight.class);
        if (weight != null){
            if (weight.status.equals(Constants.RES_OK)){
                if (weight.listItem.size() > 0){
                    listItem = weight.listItem;
                }
            }
        }

        return listItem;
    }
}
