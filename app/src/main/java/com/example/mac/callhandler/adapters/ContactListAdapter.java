package com.example.mac.callhandler.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mac.callhandler.R;
import com.example.mac.callhandler.util.ContactSharePreferances;
import com.example.mac.callhandler.util.Contacts;

import java.util.List;

/**
 * Created by Abdullah MAsood on 2/27/2016.
 */

/**
 *
 *  Base Adapter  Class .. handle Contact on List View
 */

public class ContactListAdapter extends BaseAdapter implements Filterable {
    private List<Contacts> simContact;
    private LayoutInflater layoutInflater = null;
    private Context mcontext;
    ViewHolder vHolder;
    public ContactListAdapter(Context context,List<Contacts> list_Contacts){
        mcontext=context;
        simContact=list_Contacts;
        layoutInflater = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return simContact.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return simContact.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
       // View view = convertview;

        if(convertview==null){
            convertview =  layoutInflater.inflate(R.layout.contact_list,null);
            vHolder= new ViewHolder();
            /**
             * @Find Objects Using  FindViewById
             */

            vHolder.name = (TextView)convertview.findViewById(R.id.contact_list_name);
            vHolder.number = (TextView)convertview.findViewById(R.id.contact_list_number);
            vHolder.checkContact = (CheckBox)convertview.findViewById((R.id.contact_list_select_contact));

            convertview.setTag(vHolder);
        }
        else{
            vHolder = (ViewHolder) convertview.getTag();
        }

        final Contacts contacts = simContact.get(position);
        vHolder.name.setText(contacts.getName());
        vHolder.number.setText(contacts.getNumber());
        Log.v("ContactList:", contacts.getName() + "\n");

        /**
         * @CheckBox Listener :)
         */
      vHolder.checkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){
                  Log.v("CheckBox:", contacts.getName() +"\n");
                  ContactSharePreferances.getmInstance(mcontext).saveContact(contacts.getName()
                          , contacts.getNumber(), contacts.getType());
               //
               //  ContactSharePreferances.getmInstance(mcontext).readAllContacts();
              }
              else if(!isChecked){
                  ContactSharePreferances.getmInstance(mcontext).removeContact(contacts.getName());
                  ContactSharePreferances.getmInstance(mcontext).readAllContacts();
              }

          }
      });

        return convertview;
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    /**
     * ViewHolder  Class Handle ListView . prevent reLoad  LsitView
     **/
    public class ViewHolder{

        public TextView name;
        public TextView number;
        public CheckBox checkContact;
    }
    public void selectMultipleContact(int position){

    }

}
