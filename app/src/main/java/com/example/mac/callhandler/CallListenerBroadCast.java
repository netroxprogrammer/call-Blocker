package com.example.mac.callhandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mac.callhandler.ReadMyPhoneState.ReadPhoneState;
import com.example.mac.callhandler.util.Constant;

/**
 * Created by mac on 5/19/2016.
 */
public class CallListenerBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        ReadPhoneState readPhoneState = new ReadPhoneState(context);
        Log.v(Constant.CONTACT_LOGS,"Broad cast Listen");
        telephonyManager.listen(readPhoneState, ReadPhoneState.LISTEN_CALL_STATE);
    }
}
