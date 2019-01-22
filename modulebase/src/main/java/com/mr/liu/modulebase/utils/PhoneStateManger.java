package com.mr.liu.modulebase.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by mrliu on 2018/12/28.
 * 此类用于:手机状态工具类
 */

public class PhoneStateManger {

    public void registPhoneStateListener(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            try {
                // 注册来电监听
                telephonyManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
            } catch (Exception e) {
                // 异常捕捉
                e.printStackTrace();
            }
        }
    }

    public class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (onPhoneStateChangeListener != null) {
                        onPhoneStateChangeListener.stateHangUp();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (onPhoneStateChangeListener != null) {
                        onPhoneStateChangeListener.stateOff();
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    if (onPhoneStateChangeListener != null) {
                        onPhoneStateChangeListener.stateRinging();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private OnPhoneStateChangeListener onPhoneStateChangeListener;

    public interface OnPhoneStateChangeListener {
        void stateHangUp();

        void stateOff();

        void stateRinging();
    }

    public void setOnPhoneStateChangeListener(OnPhoneStateChangeListener listener) {
        this.onPhoneStateChangeListener = listener;
    }
}
