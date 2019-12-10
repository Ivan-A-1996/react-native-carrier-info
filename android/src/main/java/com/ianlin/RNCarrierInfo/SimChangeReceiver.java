package eu.ttbox.geoping.service.slave.eventspy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import eu.ttbox.geoping.core.AppConstants;
import eu.ttbox.geoping.domain.pairing.PairingDatabase.PairingColumns;
import eu.ttbox.geoping.encoder.model.MessageActionEnum;

import com.facebook.react.bridge.Callback;

public class SimChangeReceiver extends BroadcastReceiver {

	private static final String TAG = "SimChangeReceiver";
	private static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";

	private static final String EXTRA_SIM_STATE = "ss";
	private static final String SIM_STATE_LOADED = "LOADED";
    private Callback _callback;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (ACTION_SIM_STATE_CHANGED.equals(action)) {
			String state = extras.getString(EXTRA_SIM_STATE);
			Log.w(TAG, "SIM Action : " + action + " / State : " + state);
			if (SIM_STATE_LOADED.equals(state) && _callback != null) {
                _callback.invoke(mobileNetworkOperator(context);
			}
		}
	}

	public void setCallback(Callback callback) {
	    _callback = callback;
	}

    public String mobileNetworkOperator(Context context) {
        mTelephonyManager = (TelephonyManager) reactContext.getSystemService(Context.TELEPHONY_SERVICE);
        String plmn = mTelephonyManager.getSimOperator();
        if (plmn != null && !"".equals(plmn)) {
            return plmn;
        } else {
            return "";
        }
    }
}