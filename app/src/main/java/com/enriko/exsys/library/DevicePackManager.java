package com.enriko.exsys.library;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.enriko.exsys.GetDataService;
import com.enriko.exsys.base.CustomApplication;
import com.enriko.exsys.utils.AppPreference;
import com.enriko.exsys.utils.OnResultListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DevicePackManager implements Observer {
    public DeviceData mDeviceData = new DeviceData();
    public ArrayList<DeviceData> mDeviceDatas = new ArrayList();
    boolean bGetPackId = false;
    byte[] curPack = new byte[9];
    int k = 0;
    int len = 0;
    int i;
    byte value;
    public static final int e_pack_hand_back = 72;
    public static final int e_pack_replay_confirm = 74;
    public static final int e_pack_oxyen = 71;
    public static final int e_pack_blood = 70;
    public static final int e_pack_is_new = 65;
    public static int Flag_User = 0;
    int Oxyen_count;
    int Blood_count;
    private int astolik, diastolik;
    private OnResultListener onResultListener;
    private CustomApplication application;
    Activity activity;
    AppPreference appPreference;

    public DevicePackManager(Activity activity) {
        this.activity = activity;
        application = (CustomApplication)activity.getApplication();
        application.getBloodPressureObserver().addObserver(this);
        appPreference = new AppPreference(activity);
    }

    public static int packlength(int pHead) {
        byte _return = 0;
        switch(pHead) {
            case 65:
                _return = 5;
            case 66:
            case 67:
            case 68:
            case 69:
            case 73:
            default:
                break;
            case 70:
                _return = -6;
                break;
            case 71:
                _return = -6;
                break;
            case 72:
                _return = 5;
                break;
            case 74:
                _return = 4;
        }

        return _return;
    }

    public byte arrangeMessage(byte[] buf, int length, int pType) {
        PrintBytes.printData(buf, length);
        byte _return = 0;

        for(this.i = 0; this.i < length; ++this.i) {
            this.value = buf[this.i];
            if(!this.bGetPackId) {
                if(this.value >= 0 && packlength(this.value) > 0) {
                    this.bGetPackId = true;
                    this.k = 0;
                    this.len = packlength(this.value);
                    this.curPack = new byte[this.len];
                    this.curPack[this.k++] = this.value;
                    if(this.len == 1) {
                        _return = this.processData(this.curPack, pType);
                        this.bGetPackId = false;
                    }
                } else if(this.value >= 0 && packlength(this.value) < 0) {
                    this.bGetPackId = true;
                    this.k = 0;
                    this.len = 6;
                    this.curPack = new byte[this.len];
                    this.curPack[this.k++] = this.value;
                }
            } else {
                this.curPack[this.k++] = this.value;
                if(this.k >= this.len) {
                    if(this.curPack[0] != 71 && this.curPack[0] != 70) {
                        this.bGetPackId = false;
                        _return = this.processData(this.curPack, pType);
                    } else if(this.len >= 10) {
                        this.bGetPackId = false;
                        _return = this.processData(this.curPack, pType);
                    } else {
                        byte[] _data;
                        if(this.curPack[0] == 71) {
                            _data = new byte[]{this.curPack[2], this.curPack[3], this.curPack[4], this.curPack[5]};
                            this.len = this.Data_len(_data);
                        } else if(this.curPack[0] == 70) {
                            _data = new byte[]{this.curPack[2], this.curPack[3], this.curPack[4]};
                            this.len = this.Data_len(_data);
                        }

                        _data = new byte[this.curPack.length];

                        int j;
                        for(j = 0; j < this.curPack.length; ++j) {
                            _data[j] = this.curPack[j];
                        }

                        this.curPack = new byte[this.len];

                        for(j = 0; j < _data.length; ++j) {
                            this.curPack[j] = _data[j];
                        }
                    }
                }
            }
        }

        return _return;
    }

    public byte processData(byte[] pack, int pType) {
        byte _return = 0;
        switch(pack[0]) {
            case 65:
                _return = pack[0];
            case 66:
            case 67:
            case 68:
            case 69:
            case 73:
            default:
                break;
            case 70:
                this.mDeviceData.mData_blood.clear();
                Log.e("DevicePackManager", ">>>>>>>>>>>>>>>>>>>" + this.Blood_count);
                _return = pack[0];
                if(pType == 7) {
                    if((pack[13] & 127) == 3) {
                        _return = pack[0];
                    } else {
                        _return = 0;
                    }
                }

                byte[] _blood;
                int y;
                byte[] var10;
                int var11;
                if(pType != 1 && pType != 7) {
                    if(pType == 6) {
                        var10 = this.pack_Blood(pack);

                        for(var11 = 0; var11 < this.Blood_count; ++var11) {
                            _blood = new byte[]{var10[14 + var11 * 14 + 1], var10[14 + var11 * 14 + 2], var10[14 + var11 * 14 + 3], var10[14 + var11 * 14 + 4], var10[14 + var11 * 14 + 5], var10[14 + var11 * 14 + 6], var10[14 + var11 * 14 + 7], var10[14 + var11 * 14 + 9], var10[14 + var11 * 14 + 10], var10[14 + var11 * 14 + 11], var10[14 + var11 * 14 + 12], var10[14 + var11 * 14 + 13]};
                            this.mDeviceData.mData_normal_blood.add(_blood);
                            byte var12 = _blood[2];
                            y = _blood[0] << 8 | _blood[1];
                            astolik = var12;
                            diastolik = y;
                            Log.e("JAR HOLAA", "blooddi:" + var12 + "  bloodgao:" + y);
                        }
                    }
                } else {
                    var10 = this.pack_Blood(pack);

                    for(var11 = 0; var11 < this.Blood_count; ++var11) {
                        _blood = new byte[]{var10[14 + var11 * 14 + 1], var10[14 + var11 * 14 + 2], var10[14 + var11 * 14 + 3], var10[14 + var11 * 14 + 4], var10[14 + var11 * 14 + 5], var10[14 + var11 * 14 + 6], var10[14 + var11 * 14 + 7], var10[14 + var11 * 14 + 9], var10[14 + var11 * 14 + 10], var10[14 + var11 * 14 + 11], var10[14 + var11 * 14 + 12], var10[14 + var11 * 14 + 13]};
                        this.mDeviceData.mData_blood.add(_blood);
                        int x = _blood[2] & 255;
                        y = (_blood[0] << 8 | _blood[1]) & 255;
                        astolik = x;
                        diastolik = y;
                        Log.e("JAR HOLSSSSS", "blooddi:" + x + "  bloodgao:" + y);
                    }
                }
                appPreference.setSistolik(astolik);
                appPreference.setDistolik(diastolik);
                Log.e("JAR", "e_pack_blood");
                activity.startService(new Intent(activity, GetDataService.class));
                break;
            case 71:
                _return = pack[0];
                if(pType == 8) {
                    if((pack[15] & 127) == 3) {
                        _return = pack[0];
                    } else {
                        _return = 0;
                    }
                }

                Log.e("JAR", "e_pack_oxyen");
                byte[] _oxygen_pack = this.pack_Oxygen(pack);

                for(int _data = 0; _data < this.Oxyen_count; ++_data) {
                    byte[] i = new byte[]{_oxygen_pack[16 + _data * 11 + 1], _oxygen_pack[16 + _data * 11 + 2], _oxygen_pack[16 + _data * 11 + 3], _oxygen_pack[16 + _data * 11 + 4], _oxygen_pack[16 + _data * 11 + 5], _oxygen_pack[16 + _data * 11 + 6], _oxygen_pack[16 + _data * 11 + 7], _oxygen_pack[16 + _data * 11 + 9], _oxygen_pack[16 + _data * 11 + 10]};
                    this.mDeviceData.mData_oxygen.add(i);
                    Log.e("JAR", "Oxygen:" + (i[0] & 255) + "  Plus:" + (i[1] & 255));
                }

                return _return;
            case 72:
                _return = pack[0];
                PrintBytes.printData(pack);
                Flag_User = pack[3];
                Log.e("JAR", "e_pack_hand_back");
                break;
            case 74:
                switch(pType) {
                    case 1:
                        if(pack[2] == 1) {
                            _return = 16;
                        } else {
                            _return = 17;
                        }
                        break;
                    case 2:
                        if(pack[2] == 1) {
                            _return = 32;
                        } else {
                            _return = 33;
                        }
                        break;
                    case 3:
                        if(pack[1] == 67) {
                            if(pack[2] == 1) {
                                _return = 48;
                            } else {
                                _return = 49;
                            }
                        } else {
                            Log.e("DevicePackManager", "校正时间返回");
                            if(pack[3] == 0) {
                                Log.e("DevicePackManager", "校正时间返回pack[3]=0");
                                if(pack[2] == 1) {
                                    _return = 64;
                                } else {
                                    _return = 65;
                                }
                            } else {
                                Log.e("DevicePackManager", "校正时间返回pack[3]=1");
                                _return = 66;
                            }
                        }
                    case 4:
                    default:
                        break;
                    case 5:
                        if(pack[2] == 1) {
                            _return = 80;
                        } else {
                            _return = 81;
                        }
                }

                Log.e("JAR", "e_pack_replay_confirm");
        }

        return _return;
    }

    public static byte[] unPack(byte[] pack) {
        int len = pack.length;

        int i;
        for(i = 1; i < 8; ++i) {
            pack[i] = (byte)(pack[i] & (pack[0] << 8 - i | 127));
        }

        for(i = 9; i < len; ++i) {
            pack[i] = (byte)(pack[i] & (pack[8] << len - 8 - (i - 8) | 127));
        }

        return pack;
    }

    public static byte[] doPack(byte[] pack) {
        if(pack == null) {
            return null;
        } else {
            byte len = 10;
            if(len > 0) {
                pack[2] = -128;

                for(int i = 3; i < len - 1; ++i) {
                    pack[1] = (byte)(pack[1] | (pack[i] & 128) >> 9 - i);
                    pack[i] = (byte)(pack[i] | 128);
                }
            }

            return pack;
        }
    }

    public int Data_len(byte[] pack) {
        int _data_len = 0;
        int len = pack.length;

        int _blood_count;
        for(_blood_count = 1; _blood_count < len; ++_blood_count) {
            pack[_blood_count] = (byte)(pack[_blood_count] & (pack[0] << 8 - _blood_count | 127));
        }

        if(len == 4) {
            _blood_count = (pack[1] & 255) << 16 | (pack[2] & 255) << 8 | pack[3] & 255;
            this.Oxyen_count = _blood_count;
            _data_len = 16 + _blood_count * 11;
        } else if(len == 3) {
            _blood_count = (pack[1] & 255) << 8 | pack[2] & 255;
            this.Blood_count = _blood_count;
            _data_len = 14 + _blood_count * 14;
        }

        return _data_len;
    }

    public byte[] pack_Blood(byte[] pBlood) {
        int j;
        for(j = 3; j < 10; ++j) {
            pBlood[j] = (byte)(pBlood[j] & (pBlood[2] << 10 - j | 127));
        }

        for(j = 10; j < 14; ++j) {
            pBlood[j] = (byte)(pBlood[j] & (pBlood[10] << 14 - j | 127));
        }

        for(j = 0; j < this.Blood_count; ++j) {
            int i;
            for(i = 14 + j * 14; i < 14 + j * 14 + 8; ++i) {
                pBlood[i] = (byte)(pBlood[i] & (pBlood[14 + j * 14] << 14 + j * 14 + 8 - i | 127));
            }

            for(i = 14 + j * 14 + 8; i < 14 + j * 14 + 8 + 6; ++i) {
                pBlood[i] = (byte)(pBlood[i] & (pBlood[14 + j * 14 + 8] << 14 + j * 14 + 8 + 6 - i | 127));
            }
        }

        return pBlood;
    }

    public byte[] pack_Oxygen(byte[] pOxygen) {
        int j;
        for(j = 3; j < 10; ++j) {
            pOxygen[j] = (byte)(pOxygen[j] & (pOxygen[2] << 10 - j | 127));
        }

        for(j = 10; j < 16; ++j) {
            pOxygen[j] = (byte)(pOxygen[j] & (pOxygen[10] << 16 - j | 127));
        }

        for(j = 0; j < this.Oxyen_count; ++j) {
            int i;
            for(i = 16 + j * 11; i < 16 + j * 11 + 8; ++i) {
                pOxygen[i] = (byte)(pOxygen[i] & (pOxygen[16 + j * 11] << 24 + j * 11 - i | 127));
            }

            for(i = 16 + j * 11 + 8; i < 16 + j * 11 + 8 + 3; ++i) {
                pOxygen[i] = (byte)(pOxygen[i] & (pOxygen[16 + j * 11 + 8] << 16 + j * 11 + 8 + 3 - i | 127));
            }
        }

        return pOxygen;
    }

    public void update(Observable observable, Object o) {

    }
}
