package com.ianlin.RNCarrierInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.react.bridge.Callback;

public class SimChangeReceiver extends BroadcastReceiver {

	private static final String TAG = "SimChangeReceiver";
	private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

	private static final String EXTRA_SIM_STATE = "ss";
	private static final String SIM_STATE_LOADED = "LOADED";
    private static Callback _callback;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ACTION_SIM_STATE_CHANGED.equals(action)) {
			Bundle extras = intent.getExtras();
			String state = extras.getString(EXTRA_SIM_STATE);
			Log.w(TAG, "SIM Action : " + action + " / State : " + state);
			if (SIM_STATE_LOADED.equals(state) && _callback != null) {
                _callback.invoke(mobileNetworkOperator(context));
			}
		}
	}

	public static void setCallback(Callback callback) {
	    _callback = callback;
	}

    public String mobileNetworkOperator(Context context) {
		TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String plmn = mTelephonyManager.getSimOperator();
        if (plmn != null && !"".equals(plmn)) {
            return plmn;
        } else {
            return "";
        }
    }
}