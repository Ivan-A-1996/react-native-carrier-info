package com.ianlin.RNCarrierInfo;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.provider.Settings;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Callback;

public class RNCarrierInfoModule extends ReactContextBaseJavaModule {
    private final static String TAG = RNCarrierInfoModule.class.getCanonicalName();
    private final static String E_NO_CARRIER_NAME = "no_carrier_name";
    private final static String E_NO_ISO_COUNTRY_CODE = "no_iso_country_code";
    private final static String E_NO_MOBILE_COUNTRY_CODE = "no_mobile_country_code";
    private final static String E_NO_MOBILE_NETWORK = "no_mobile_network";
    private final static String E_NO_NETWORK_OPERATOR = "no_network_operator";
    private TelephonyManager mTelephonyManager;

    public RNCarrierInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mTelephonyManager = (TelephonyManager) reactContext.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @Override
    public String getName() {
        return "RNCarrierInfo";
    }

    @ReactMethod
    public void carrierName(Promise promise) {
        String carrierName = mTelephonyManager.getSimOperatorName();
        if (carrierName != null && !"".equals(carrierName)) {
            promise.resolve(carrierName);
        } else {
            promise.reject(E_NO_CARRIER_NAME, "No carrier name");
        }
    }

    @ReactMethod
    public void isoCountryCode(Promise promise) {

        String iso = "";

        try{
            if(mTelephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT)
                iso = mTelephonyManager.getSimCountryIso();
            else
                iso = mTelephonyManager.getNetworkCountryIso();

            if (iso != null)
                promise.resolve(iso);
             else
                 promise.reject(E_NO_ISO_COUNTRY_CODE, "No iso country code");
        }catch(Exception e){
            promise.reject(E_NO_ISO_COUNTRY_CODE, "No iso country code");
        }
    }

    // returns MCC (3 digits)
    @ReactMethod
    public void mobileCountryCode(Promise promise) {
        try {
            String networkOperator = null;
            if (isAirplaneModeOn(getReactApplicationContext())) {
                networkOperator = mTelephonyManager.getSimOperator();
            } else {
                networkOperator = mTelephonyManager.getNetworkOperator();
            }
            if (networkOperator != null && networkOperator.length() > 2) {
                int mcc = Integer.parseInt(networkOperator.substring(0, 3));
                promise.resolve(mcc);
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            promise.reject(E_NO_MOBILE_COUNTRY_CODE, "No mobile country code");
        }
    }

    // returns MNC (2 or 3 digits)
    @ReactMethod
    public void mobileNetworkCode(Promise promise) {
        try {
            String networkOperator = null;
            if (isAirplaneModeOn(getReactApplicationContext())) {
                networkOperator = mTelephonyManager.getSimOperator();
            } else {
                networkOperator = mTelephonyManager.getNetworkOperator();
            }
            if (networkOperator != null && networkOperator.length() > 2) {
                int mnc = Integer.parseInt(networkOperator.substring(3));
                promise.resolve(mnc);
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            promise.reject(E_NO_MOBILE_NETWORK, "No mobile network code");
        }
    }

    // return MCC + MNC (5 or 6 digits), e.g. 20601
    @ReactMethod
    public void mobileNetworkOperator(Promise promise) {
        String plmn = mTelephonyManager.getSimOperator();
        if (plmn != null && !"".equals(plmn)) {
            promise.resolve(plmn);
        } else {
            promise.reject(E_NO_NETWORK_OPERATOR, "No mobile network operator");
        }
    }

    @ReactMethod
    public void addCarrierChangeListener(Callback successCallback)
    {
        SimChangeReceiver.setCallback(successCallback);
    }

    /**
     * Gets the state of Airplane Mode.
     *
     * @param context
     * @return true if enabled.
     */
    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }
}
