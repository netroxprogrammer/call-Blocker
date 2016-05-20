package com.example.mac.callhandler;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import javax.crypto.Mac;

@SuppressWarnings("desprecation")
public class AllNumbersList extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_numbers_list);

      //  TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("myTabName")
                .setContent(new Intent(this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

    }
}
