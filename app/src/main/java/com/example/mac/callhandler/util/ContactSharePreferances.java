package com.example.mac.callhandler.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.Map;

/**
 * Created by mac on 4/19/2016.
 */

public class ContactSharePreferances {

    private static ContactSharePreferances mInstance;
    private Context context;
    private SharedPreferences mySharePref;
    private static final String contactName = "name";
    private static final String contactNumber = "number";
    private static final String sharePrefName = "contacts";
    private static final String contactType = "type";


    private ContactSharePreferances(Context context) {
        this.context = context;
        mySharePref = context.getSharedPreferences(sharePrefName, Context.MODE_PRIVATE);
    }

    public static ContactSharePreferances getmInstance(Context c) {
        if (mInstance == null) {
            mInstance = new ContactSharePreferances(c);
        }
        return mInstance;
    }

    public void saveContact(String name, String number, String type) {
        SharedPreferences.Editor editor = mySharePref.edit();
        if (checkContact(number)) {
            Log.v(Constant.CONTACT_LOGS, name + " already Exist...");
        } else {
            editor.putString(name+" "+type, number);
           // editor.putString(number, type);
            editor.commit();
            Log.v(Constant.CONTACT_LOGS, "Save Data in Share Preferance");
            Log.v(Constant.CONTACT_LOGS, "Check type.... " + type);
        }
    }

    /**
     * @DuplicateName Check  dublication of names
     */

    public boolean checkContact(String number) {
        Map<String, ?> keys = mySharePref.getAll();
        for (Map.Entry<String, ?> values : keys.entrySet()) {
//           / Log.v(Constant.CONTACT_LOGS, "AllData.... "+values.getValue().toString());
            if (values.getValue().toString().equalsIgnoreCase(number)) {
                Log.v(Constant.CONTACT_LOGS, "Check Data.... " + values.getValue().toString());
                return true;
            }

        }
        return false;
    }

    /**
     * @Remove number
     */
    public void removeContact(String name) {
        SharedPreferences.Editor editor = mySharePref.edit();
        editor.remove(name);
        editor.apply();
    }

    /**
     * @Read All Data from SharePreferance
     */
    public void readAllContacts() {
        Map<String, ?> keys = mySharePref.getAll();
        for (Map.Entry<String, ?> values : keys.entrySet()) {
            Log.v(Constant.CONTACT_LOGS, "All  Data.... " + values.getValue().toString());

        }
    }


    /**
     * @Read All Data from SharePreferance in form Of Map
     */
    public Map<String, ?> readAllContactsUsingMap() {
        Map<String, ?> keys = mySharePref.getAll();

        return keys;
    }

}
