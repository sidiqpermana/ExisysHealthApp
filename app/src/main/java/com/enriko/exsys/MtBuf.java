package com.enriko.exsys;

import android.app.Activity;
import android.util.Log;

import com.enriko.exsys.base.CustomApplication;
import com.enriko.exsys.library.DeviceCommand;
import com.enriko.exsys.library.DevicePackManager;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;


public class MtBuf implements Observer {
	private static final String TAG = "com.testBlueTooth.Mtbuf";
	public static Vector<Integer> m_buf = null;

   public static final int e_pack_pressure_back = 0x46;

	public synchronized int Count() {
		return m_buf.size();
	}

	DevicePackManager mPackManager = null;

	private DeviceData mDeviceData;
	public static final int e_pack_hand_back = 0x48;
	public static final int e_pack_oxygen_back = 0x47;
	private int mType = 0;
	Activity activity;
	CustomApplication application;
	public MtBuf(Activity activity){
		this.activity = activity;
		mPackManager = new DevicePackManager(activity);
        m_buf = new Vector<Integer>();
		application = (CustomApplication)activity.getApplication();
		application.getBloodPressureObserver().addObserver(this);
	}

	public synchronized void write(byte[] buf, int count,
			OutputStream pOutputStream) throws Exception {

		int state = mPackManager.arrangeMessage(buf, count, mType);
		int x = DevicePackManager.Flag_User;
		switch (state) {
		case e_pack_hand_back:
			switch (mType) {
			case 9:
				mType = 5;
				pOutputStream.write(DeviceCommand.DELETE_BP());
				break;
			case 0:
				mType = 3;
				pOutputStream.write(DeviceCommand.correct_time_notice);
				break;
//			case 1:
//				mType = 2;
//				pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
//				break;
//			case 7:
//				mType = 8;
//				pOutputStream.write(DeviceCommand.REQUEST_OXYGEN());
//				break;
//			case 2:
//				mType = 5;
//				pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
//				break;
//			case 8:
//				mType = 5;
//				pOutputStream.write(DeviceCommand.DELETE_OXYGEN());
//				break;
			case 3:
				mType = 1;

				if (x == 0x11) {
					mType = 7;// 三个用户
				} else {
					mType = 1;// 单用户
				}

				pOutputStream.write(DeviceCommand.REQUEST_BLOOD_PRESSURE());
				break;
			}
			break;
		case 0x30:// 确认校正时间正确
			pOutputStream.write(DeviceCommand.Correct_Time());
			break;
		case 0x40:// 校正时间正确
			pOutputStream.write(DeviceCommand.REQUEST_HANDSHAKE());
			break;
		case e_pack_pressure_back:
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {////防止最后一条命令血压设备接收不到
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<byte[]> _dataList = mPackManager.mDeviceData.mData_blood;
			int _size = _dataList.size();
			DeviceData _mData = new DeviceData(null);
			for (int i = 0; i < _size; i++) {
				byte[] _byte = _dataList.get(i);
				byte[] _data = new byte[10];

					int hiPre = ((_data[5] << 8 )|( _data[6] & 0xff)) & 0xffff;// 高压
					int lowPre = _data[7] & 0xff;// 低压
				}
			if (_size == 0) {
				pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

				Log.e(TAG, "-------Pressure-------");
				Log.e(TAG, "血压血压血压血压血压");
			
			}else{
				pOutputStream.write(DeviceCommand.REPLAY_CONTEC08A());

			}


			break;
		}
		
	}

	public void update(Observable observable, Object o) {

	}
}
