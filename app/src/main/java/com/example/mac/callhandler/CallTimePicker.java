package com.example.mac.callhandler;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.mac.callhandler.util.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

public class CallTimePicker extends AppCompatActivity implements View.OnClickListener{
    private Calendar calendar;
    private String format = "";
    int  hourse,minuts;
    TimePicker time;
    Button b;
    NumberPicker   np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_time_picker);

        Intent intent = getIntent();
        String number = intent.getStringExtra("number");
        b = (Button)findViewById(R.id.Addtime);

        np = (NumberPicker) findViewById(R.id.numberPicker);

        b.setOnClickListener(this);
         }

    @Override
    public void onClick(View v) {
        String[] nums = new String[20];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        np.setMinValue(1);
        np.setMaxValue(20);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(nums);
        np.setValue(1);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                String Old = "Old Value : ";
                String New = "New Value : ";


            }
        });
    }
}
