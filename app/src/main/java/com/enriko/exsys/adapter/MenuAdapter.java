package com.enriko.exsys.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enriko.exsys.R;
import com.enriko.exsys.utils.Constants;

/**
 * Created by Sidiq on 07/06/2015.
 */
public class MenuAdapter extends BaseAdapter {

    Activity activity;

    public MenuAdapter(Activity activity){
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return Constants.menus.length;
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
            view = inflater.inflate(R.layout.item_menu, null);
            holder.txtTitle = (TextView)view.findViewById(R.id.txt_menu_title);
            holder.txtDescription = (TextView)view.findViewById(R.id.txt_menu_desc);
            holder.imgItem = (ImageView)view.findViewById(R.id.img_item_menu);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.imgItem.setImageResource(Constants.menu_icon[position]);
        holder.txtTitle.setText(Constants.menus[position][0]);
        holder.txtDescription.setText(Constants.menus[position][1]);
        return view;

    }

    static class ViewHolder{
        ImageView imgItem;
        TextView txtTitle, txtDescription;
    }
}
