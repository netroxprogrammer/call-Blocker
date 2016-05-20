package com.example.mac.callhandler;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.example.mac.callhandler.util.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button blackList, whiteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setXML();

    }
    public void setXML(){
        blackList = (Button)findViewById(R.id.activity_main_blackList);
        whiteList = (Button)findViewById(R.id.activity_main_whiteList);
        blackList.setOnClickListener(this);
        whiteList.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
       if(id==R.id.activity_main_blackList){
           Intent intent = new Intent(MainActivity.this,HandleContacs.class);
           intent.putExtra("type","blackList");
           startActivity(intent);
       }
        if(id==R.id.activity_main_whiteList){
            Intent intent = new Intent(MainActivity.this,HandleContacs.class);
            intent.putExtra("type","whiteList");
            startActivity(intent);
        }
    }


}
