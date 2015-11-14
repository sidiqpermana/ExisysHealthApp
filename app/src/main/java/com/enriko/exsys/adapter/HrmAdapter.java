package com.enriko.exsys.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enriko.exsys.R;
import com.enriko.exsys.model.HrmItem;

import java.util.ArrayList;

/**
 * Created by Sidiq on 07/06/2015.
 */
public class HrmAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<HrmItem> listItem;

    public HrmAdapter(Activity activity, ArrayList<HrmItem> listItem) {
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_hrm, null);
            holder.txtHrmPoint = (TextView)view.findViewById(R.id.txt_item_hrm_point);
            holder.txtDateTaken = (TextView)view.findViewById(R.id.txt_hrm_date);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.txtHrmPoint.setText(listItem.get(position).hrm+"");
        holder.txtDateTaken.setText(listItem.get(position).tanggal);

        return view;
    }

    static class ViewHolder{
        TextView txtHrmPoint, txtDateTaken;
    }
}
