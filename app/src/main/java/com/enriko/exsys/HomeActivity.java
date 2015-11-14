package com.enriko.exsys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.enriko.exsys.adapter.ViewPagerAdapter;
import com.enriko.exsys.base.BaseActivity;
import com.enriko.exsys.base.VolleySingleton;
import com.enriko.exsys.custom_ui.SlidingTabLayout;
import com.enriko.exsys.utils.URLs;

import java.util.HashMap;
import java.util.Map;


public class HomeActivity extends BaseActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Health Check","Report", "Advice"};
    int Numboftabs = Titles.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            postLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void postLogout() {
        // TODO Auto-generated method stub
        final ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
        dialog.setMessage("Harap tunggu...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,
                URLs.LOGOUT,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        parsingResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                utils.printLog("Gagal dalam menambah data");
            }
        }){
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                // TODO Auto-generated method stub
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", preferences.getToken());
                return params;
            }

        };

        VolleySingleton.getInstance(HomeActivity.this).addToRequestQueue(request);
    }

    public static void toHomeActivity(Activity activity){
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }
}
