package com.example.mac.callhandler.ReadMyPhoneState;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mac.callhandler.util.Constant;
import com.example.mac.callhandler.util.ContactSharePreferances;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 5/19/2016.
 */
public class ReadPhoneState extends PhoneStateListener {
    Context context;
    AudioManager am;
    public ReadPhoneState(Context context){
        this.context=context;
        am=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

    }
    @Override
    public void onCallStateChanged(int state,String callingNumber){
        super.onCallStateChanged(state,callingNumber);
        switch (state){
            case TelephonyManager.CALL_STATE_IDLE:
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                blockCall(callingNumber);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                blockCall(callingNumber);
                break;
        }
    }


    public void blockCall(String callingNumber){
        try{
            Log.v("Call_State", callingNumber);
            Map<String, ?> keys = ContactSharePreferances.getmInstance(context).readAllContactsUsingMap();
            for (Map.Entry<String, ?> values : keys.entrySet()) {

                if (checkListType(values.getKey().toString())) {
                    if (callingNumber.equalsIgnoreCase(values.getValue().toString())) {
                        rejectCall();
                        Log.v(Constant.CONTACT_LOGS, "Reject  Call Number is ----- " + values.getValue().toString());
                    }
                } else {
                    scielentMpobile();

                    //  rejectCall();
                    Log.v(Constant.CONTACT_LOGS, " Scilent Mobile ----- " + values.getValue().toString());
                }


            }


                // am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


          /*  Log.v("Call_State",callingNumber);
            TelephonyManager telephonyManager =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> c =Class.forName(telephonyManager.getClass().getName());
            Method method = c.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            ITelephony Itelephonyservices=(ITelephony)method.invoke(telephonyManager);
           // Itelephonyservices=(ITelephony)method.invoke(telephonyManager);
            Itelephonyservices.silenceRinger();
            Itelephonyservices.endCall();*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void rejectCall(){


        try {
            TelephonyManager telephonyManager =(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            // Get the getITelephony() method
            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method method = classTelephony.getDeclaredMethod("getITelephony");
            // Disable access check
            method.setAccessible(true);
            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = method.invoke(telephonyManager);
            // Get the endCall method from ITelephony
            Class<?> telephonyInterfaceClass =Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public  void scielentMpobile(){
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    public  boolean checkListType(String text){
        String[] sentence = text.split(" ");
        for(String word: sentence)
        {
            if(word.equals(Constant.BLACK_LIST))
                return true;
        }
        return false;
    }
}


