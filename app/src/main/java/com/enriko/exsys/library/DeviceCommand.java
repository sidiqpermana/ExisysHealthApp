package com.enriko.exsys.library;


import java.util.Calendar;

public class DeviceCommand {
    public static byte[] correct_time_notice = new byte[]{(byte)67, (byte)66, (byte)2, (byte)-1, (byte)4, (byte)0};
    public static byte BP_USER_ALL = 20;
    public static byte BP_USER_1 = 17;
    public static byte BP_USER_2 = 18;
    public static byte BP_USER_3 = 19;
    public static byte BOLD_USER_ALL = 36;
    public static byte BOLD_USER_1 = 33;
    public static byte BOLD_USER_2 = 34;
    public static byte BOLD_USER_3 = 35;
    public static byte BOLD_DELETE_ALL = 40;
    public static byte BOLD_DELETE_1 = 37;
    public static byte BOLD_DELETE_2 = 38;
    public static byte BOLD_DELETE_3 = 39;
    public static byte BP_DELETE_ALL = 24;
    public static byte BP_DELETE_1 = 21;
    public static byte BP_DELETE_2 = 22;
    public static byte BP_DELETE_3 = 23;

    public DeviceCommand() {
    }

    public static byte[] REQUEST_IS_NEW_DEVICES() {
        byte[] _commd_is_new_devices = new byte[]{(byte)64, (byte)-113, (byte)-1, (byte)-2, (byte)-3, (byte)-4};
        return _commd_is_new_devices;
    }

    public static byte[] REPLAY_CONTEC08A() {
        return new byte[]{(byte)74, (byte)70, (byte)1, (byte)0};
    }

    public static byte[] REPLAY_ABPM50() {
        return new byte[]{(byte)74, (byte)67, (byte)1, (byte)0};
    }

    public static byte[] REQUEST_HANDSHAKE() {
        byte[] _commd_Handshake = new byte[]{(byte)66, (byte)-113, (byte)-1, (byte)-2, (byte)-3, (byte)-4};
        return _commd_Handshake;
    }

    public static byte[] REQUEST_BLOOD_PRESSURE() {
        byte[] _commd_blood_pressure = new byte[]{(byte)67, (byte)66, (byte)1, (byte)7, (byte)5, (byte)0};
        return _commd_blood_pressure;
    }

    public static byte[] REQUEST_BLOOD_PRESSURE_NEW_Version() {
        byte[] _commd_blood_pressure = new byte[]{(byte)67, (byte)66, (byte)1, (byte)7, (byte)5, (byte)0};
        return _commd_blood_pressure;
    }

    public static byte[] REQUEST_NORMAL_BLOOD_PRESSURE() {
        byte[] _commd_normal_blood_pressure = new byte[]{(byte)67, (byte)0, (byte)1, (byte)3, (byte)5, (byte)0};
        return _commd_normal_blood_pressure;
    }

    public static byte[] REQUEST_AMBULATORY_BLOOD_PRESSURE() {
        byte[] _commd_ambulatory_blood_pressure = new byte[]{(byte)67, (byte)4, (byte)1, (byte)1, (byte)2, (byte)0};
        return _commd_ambulatory_blood_pressure;
    }

    public static byte[] REQUEST_OXYGEN() {
        byte[] _commd_oxygen = new byte[]{(byte)67, (byte)66, (byte)1, (byte)7, (byte)3, (byte)0};
        return _commd_oxygen;
    }

    public static byte[] Correct_Time() {
        int mYear = Calendar.getInstance().get(Calendar.YEAR) - 2000;
        int mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int mHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mMinutes = Calendar.getInstance().get(Calendar.MINUTE);
        int mSeconds = Calendar.getInstance().get(Calendar.SECOND);
        byte[] SET_DATE = new byte[]{(byte)76, (byte)66, (byte)-128, (byte)mYear, (byte)mMonth, (byte)mDay, (byte)mHours, (byte)mMinutes, (byte)mSeconds, (byte)0};
        DevicePackManager.doPack(SET_DATE);
        return SET_DATE;
    }

    public static byte[] Correct_Time_Abpm50() {
        int mYear = Calendar.getInstance().get(Calendar.YEAR) - 2000;
        int mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int mHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int mMinutes = Calendar.getInstance().get(Calendar.MINUTE);
        int mSeconds = Calendar.getInstance().get(Calendar.SECOND);
        byte[] SET_DATE = new byte[]{(byte)76, (byte)-124, (byte)-128, (byte)mYear, (byte)mMonth, (byte)mDay, (byte)mHours, (byte)mMinutes, (byte)mSeconds, (byte)0};
        DevicePackManager.doPack(SET_DATE);
        return SET_DATE;
    }

    public static byte[] DELETE_OXYGEN() {
        byte[] _delete_oxygen = new byte[]{(byte)67, (byte)66, (byte)3, (byte)7, (byte)3, (byte)0};
        return _delete_oxygen;
    }

    public static byte[] DELETE_BP() {
        byte[] _delete_bp = new byte[]{(byte)67, (byte)66, (byte)3, (byte)7, (byte)2, (byte)0};
        return _delete_bp;
    }

    public static byte[] command_synchronizationTime(byte[] pTime) {
        if(pTime.length != 6) {
            return null;
        } else {
            byte[] _cmd = new byte[]{(byte)25, pTime[0], pTime[1], pTime[2], pTime[3], pTime[4], pTime[5]};
            return _cmd;
        }
    }
}
