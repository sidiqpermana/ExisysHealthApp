package com.enriko.exsys.model;

import com.enriko.exsys.base.model.BaseModel;
import com.enriko.exsys.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sidiq on 07/06/2015.
 */
public class Hrm extends BaseModel {
    @SerializedName("data")
    public ArrayList<HrmItem> listItem = new ArrayList<>();

    public static ArrayList<HrmItem> getListItem(String response){
        ArrayList<HrmItem> listItem = null;
        Gson gson = new Gson();
        Hrm hrm = gson.fromJson(response, Hrm.class);
        if (hrm != null){
            if (hrm.status.equals(Constants.RES_OK)){
                if (hrm.listItem.size() > 0){
                    listItem = new ArrayList<>();
                    listItem = hrm.listItem;
                }
            }
        }

        return listItem;
    }
}
