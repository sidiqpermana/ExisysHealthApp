package com.enriko.exsys;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.enriko.exsys.base.BaseActivity;
import com.enriko.exsys.base.CustomApplication;
import com.enriko.exsys.base.VolleySingleton;
import com.enriko.exsys.model.GeneralResponse;
import com.enriko.exsys.utils.AppPreference;
import com.enriko.exsys.utils.URLs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class AddNewBloodPressuareActivity extends BaseActivity implements ICallBack, Observer{
    private ToggleButton tglBtOnOff;
    private Button btnDiscroverable, btnGetData;
    private TextView txtConnectTo;
    private TextView txtResult, txtProgress;
    private LinearLayout lnProgress;

    static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    BluetoothAdapter btAdapt;
    private BluetoothChatService mChatService;
    public MtBuf m_mtbuf = null;
    public static BluetoothSocket btSocket;
    private CustomApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_blood_pressuare);

        initUI();
        initLib();
        initProcess();
        initEvent();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (mChatService == null)
            setupChat();

    }

    private CallBack call;
    private void setupChat() {
        // TODO Auto-generated method stub
        mChatService = new BluetoothChatService(AddNewBloodPressuareActivity.this,call);
    }

    @Override
    public void initUI() {
        super.initUI();

        tglBtOnOff = (ToggleButton)findViewById(R.id.tgl_bluetooth_activated);
        btnDiscroverable = (Button)findViewById(R.id.btn_bluetooth_discoverable);
        btnGetData = (Button)findViewById(R.id.btn_bluetooth_get_data);
        txtConnectTo = (TextView)findViewById(R.id.txt_connect_to);
        txtResult = (TextView)findViewById(R.id.txt_item_bp);
        txtProgress = (TextView)findViewById(R.id.txt_new_blood_pressure_msg);
        lnProgress = (LinearLayout)findViewById(R.id.ln_progress);

        getSupportActionBar().setTitle("Tambah Data BP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initLib() {
        super.initLib();
        m_mtbuf = new MtBuf(AddNewBloodPressuareActivity.this);
        call = new CallBack(this.m_mtbuf, this);
        application = (CustomApplication)getApplication();
        application.getBloodPressureObserver().addObserver(this);
    }

    @Override
    public void initProcess() {
        super.initProcess();
        btAdapt = BluetoothAdapter.getDefaultAdapter();
        if (btAdapt.getState() == BluetoothAdapter.STATE_OFF)// 读取蓝牙状态并显示
            tglBtOnOff.setChecked(false);
        else if (btAdapt.getState() == BluetoothAdapter.STATE_ON)
            tglBtOnOff.setChecked(true);

        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(searchDevices, intent);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (btAdapt.getState() == BluetoothAdapter.STATE_OFF) {
                    Toast.makeText(AddNewBloodPressuareActivity.this, "Bluetooth tidak aktif. Aktifkan terlebih dahulu " +
                            "dan pastikan sudah terhubung ke device (pairing)", Toast.LENGTH_LONG).show();
                    return;
                }
                txtConnectTo.setText(btAdapt.getAddress());
                btAdapt.startDiscovery();
                btnGetData.setEnabled(false);
                lnProgress.setVisibility(View.VISIBLE);

                //postBpm(120, 80);
            }
        });

        btnDiscroverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discoverableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(
                        BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);
            }
        });

        tglBtOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tglBtOnOff.isChecked() == false)
                    btAdapt.enable();
                else if (tglBtOnOff.isChecked() == true)
                    btAdapt.disable();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_blood_pressuare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            try {
                if (btSocket != null)
                    btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int[] m_buf = new int[0];
    public void call() {
        Vector<Integer> _ver = MtBuf.m_buf;
        for (int i = 0; i < _ver.size(); i++) {
            Log.i("............", Integer.toHexString(_ver.get(i)&0xFF));

        }

    }

    private BroadcastReceiver searchDevices = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            Object[] lstName = b.keySet().toArray();

            for (int i = 0; i < lstName.length; i++) {
                String keyName = lstName[i].toString();
                Log.e(keyName, String.valueOf(b.get(keyName)));
            }

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName()==null) {
                    return;
                }
                if (device.getName().contains("NIBP03")||device.getName().equals(Constants.DEVICE_NAME2)) {
                    Log.e("---------", "-----------");
                    if (mChatService != null)
                        mChatService.stop();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mChatService.start();
                    mChatService.connect(device);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(searchDevices);
        super.onDestroy();
    }

    public void update(Observable observable, Object o) {
        final AppPreference appPreference = new AppPreference(AddNewBloodPressuareActivity.this);
        runOnUiThread(new Runnable() {
            public void run() {
                lnProgress.setVisibility(View.GONE);
                txtResult.setVisibility(View.VISIBLE);
                txtResult.setText("Sistolik - Distolik Anda : " + appPreference.getDistolik() + " - " + appPreference.getSistolik());
                postBpm(appPreference.getDistolik(), appPreference.getSistolik());
            }
        });
    }

    public static void toAddNewBloodPressureActivity(Activity activity){
        activity.startActivity(new Intent(activity, AddNewBloodPressuareActivity.class));
    }

    private void postBpm(final int sistolik, final int distolik ){
        lnProgress.setVisibility(View.VISIBLE);
        txtProgress.setText("Post to server");
        StringRequest request = new StringRequest(Request.Method.POST, URLs.SET_BPM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                lnProgress.setVisibility(View.GONE);
                parsingResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lnProgress.setVisibility(View.GONE);
                utils.showToast("Tidak dapat terhubung ke server");
                utils.showToast(error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fdiastolik", String.valueOf(distolik));
                params.put("fsistolik", String.valueOf(sistolik));
                params.put("uid", preferences.getUser().userInfo.id);
                params.put("fdate", utils.getCurrentDate());

                utils.printLog(String.valueOf(distolik) + " "+ String.valueOf(sistolik) + " "+
                        preferences.getUser().userInfo.id+" "+utils.getCurrentDate());

                return params;
            }
        };

        VolleySingleton.getInstance(AddNewBloodPressuareActivity.this).addToRequestQueue(request);
    }

    @Override
    public void parsingResponse(String response) {
        super.parsingResponse(response);
        if (GeneralResponse.getGeneralResponse(response).status.equals("OK")){
            utils.showToast("Data berhasil dikirim");
        }else{
            utils.showToast(GeneralResponse.getGeneralResponse(response).message);
        }
    }
}
