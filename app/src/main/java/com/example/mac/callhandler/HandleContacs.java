package com.example.mac.callhandler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mac.callhandler.adapters.ContactListAdapter;
import com.example.mac.callhandler.util.Constant;
import com.example.mac.callhandler.util.ContactSharePreferances;
import com.example.mac.callhandler.util.Contacts;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class HandleContacs extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private List<Contacts> simContacts;
    ContactListAdapter contactListAdapter;
    String savetype = null;
    Context context = null;
    ListView contactlsitView;
    EditText activity_handle_addContact_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_numbers);
        setXML();
        context = HandleContacs.this;
        Intent data = getIntent();
        savetype = data.getStringExtra("type");
        Log.v(Constant.CONTACT_LOGS, "Black List Active ===== " + data.getStringExtra("type"));
        sharedPreferences = getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        simContacts = new ArrayList<>();
        callAdapter(getContactList());
        Map<String, ?> keys = ContactSharePreferances.getmInstance(context).readAllContactsUsingMap();
        for (Map.Entry<String, ?> values : keys.entrySet()) {
            if (values.getKey().equalsIgnoreCase(Constant.BLACK_LIST)) {

                Log.v(Constant.CONTACT_LOGS, "Reject  Call Number is ----- " + values.getValue().toString());
            }
        }
        contactlsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t = (TextView) view.findViewById(R.id.contact_list_number);
                 Toast.makeText(getApplicationContext(), t.getText().toString(), Toast.LENGTH_SHORT).show();
               Intent intent= new Intent(getApplicationContext(), CallTimePicker.class);
                intent.putExtra("number", t.getText().toString());
                startActivity(intent);

            }
        });
    }

    /**
     * Call Adapter
     *
     * @param myContacts
     */
    public void callAdapter(List<Contacts> myContacts) {
        contactListAdapter = new ContactListAdapter(context, myContacts);
        contactlsitView.setAdapter(contactListAdapter);
        contactlsitView.setTextFilterEnabled(true);




    }
    /**
     * @Param  SetXML
     */

    public void setXML(){
        contactlsitView = (ListView) findViewById(R.id.activity_handle_contactList);
        //activity_handle_addContact_text = (EditText)findViewById(R.id.activity_handle_addContact_text);

    }

    /**
     * @Get Contact List From Sim
     */

    public List<Contacts> getContactList() {

        //  SharedPreferences.Editor editor = sharedPreferences.edit();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contacts contacts = new Contacts();
            contacts.setName(name);
            contacts.setNumber(phoneNumber);
            contacts.setType(savetype);
            simContacts.add(contacts);


            Log.i("Numbers:", name + " == " + phoneNumber);
        }
        Log.i("SizeConatct:", String.valueOf(simContacts.size()));
        // editor.commit();
        for (Contacts c : simContacts) {
            Log.i("Contacts", c.getName());
        }
        //Log.v("Numbers:",simContacts.toString());
        phones.close();
        return simContacts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add_Contact:
                addSingleContact();
                return true;
            case R.id.menu_add_Contact_CallLogs:
                simContacts.clear();
                callAdapter(callLogContacts());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * @Add Single Contact
     */

    public void addSingleContact() {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.manual_contact, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set manual_contact.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userName = (EditText) promptsView.findViewById(R.id.manual_contact_name_txt);
        final EditText userNumber = (EditText) promptsView.findViewById(R.id.manual_contact_number_txt);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                ContactSharePreferances.getmInstance(context).
                                        saveContact(userName.getText().toString(), userNumber.getText().toString(), savetype);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public List<Contacts> callLogContacts(){
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor c = managedQuery(allCalls, null, null, null, null);
        while(c.moveToNext()) {
            String number = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
            String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
            String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
            int type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going

            Contacts contacts = new Contacts();
            contacts.setName(name);
            contacts.setNumber(number);
            contacts.setType(savetype);
            simContacts.add(contacts);

        }
        return simContacts;
    }

}
