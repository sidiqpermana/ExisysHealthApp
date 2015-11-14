package com.enriko.exsys.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.enriko.exsys.BpmActivity;
import com.enriko.exsys.HrmActivity;
import com.enriko.exsys.R;
import com.enriko.exsys.WeightActivity;
import com.enriko.exsys.adapter.MenuAdapter;
import com.enriko.exsys.base.BaseFragment;


public class HealthyFragment extends BaseFragment {

    private ListView lvItem;

    public HealthyFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthycheck, container, false);
        lvItem = (ListView)view.findViewById(R.id.lv_item);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvItem.setAdapter(new MenuAdapter(getActivity()));
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    HrmActivity.toHrmActivity(getActivity());
                }

                if (position == 1){
                    BpmActivity.toBpmActivity(getActivity());
                }

                if (position == 3){
                    WeightActivity.toWeightActivity(getActivity());
                }
            }
        });
    }
}
