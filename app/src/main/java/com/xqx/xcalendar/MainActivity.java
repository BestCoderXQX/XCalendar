package com.xqx.xcalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xqx.xcalendar.entity.SelectDayEntity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public SelectDayEntity startDay;
    public SelectDayEntity stopDay;

    private Button btnStart;
    private TextView txtTime;
    private int VALID_TIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        initView();
        initEvent();

    }

    private void initVariables() {
        startDay = new SelectDayEntity(0, 0, 0, 0);
        stopDay = new SelectDayEntity(-1, -1, -1, -1);
    }

    private void initView() {

        btnStart = (Button) findViewById(R.id.btnStart);
        txtTime = (TextView) findViewById(R.id.txtStart);

    }

    private void initEvent() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int day = c.get(Calendar.DAY_OF_MONTH);
                startDay.setYear(year);
                startDay.setMonth(month);
                startDay.setDay(day);
                Intent intentTime = new Intent();
                intentTime.setClass(MainActivity.this, ValidTimeActivity.class);
                intentTime.putExtra("startDay", startDay);
                intentTime.putExtra("stopDay", stopDay);
                startActivityForResult(intentTime, VALID_TIME);
            }
        });
    }

    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == VALID_TIME) {
            startDay = data.getParcelableExtra("startDay");
            stopDay = data.getParcelableExtra("stopDay");
            if (startDay.getYear() != 0 && stopDay.getYear() != -1) {
                txtTime.setText(startDay.getMonth() + "月" + startDay.getDay() + "日"+"-- " + stopDay.getMonth() + "月" + stopDay.getDay() + "日");
            } else {
                txtTime.setText("时间");
            }
        }
    }
}
