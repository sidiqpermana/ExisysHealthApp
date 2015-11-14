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
public class Bpm extends BaseModel implements Serializable {
    @SerializedName("data")
    public ArrayList<BpmItem> listItem = new ArrayList<>();

    public static ArrayList<BpmItem> getListItem(String response){
        ArrayList<BpmItem> listItem = null;
        Gson gson = new Gson();
        Bpm bpm = gson.fromJson(response, Bpm.class);
        if (bpm != null){
            if (bpm.status.equals(Constants.RES_OK)){
                if (bpm.listItem.size() > 0){
                    listItem = bpm.listItem;
                }
            }
        }

        return listItem;
    }
}
