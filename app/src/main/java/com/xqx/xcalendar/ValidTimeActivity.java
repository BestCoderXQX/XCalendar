package com.xqx.xcalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xqx.xcalendar.editValidTime.ValidTimeAdapter;
import com.xqx.xcalendar.entity.PlanTimeEntity;
import com.xqx.xcalendar.entity.SelectDayEntity;

import java.util.ArrayList;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/*
*   http://www.cnblogs.com/xqxacm/p/6297151.html
* */
/*
* @author xqx
* @emil djlxqx@163.com
* create at 2017/4/20
* description: 选择方案的有效时间  最多七天
*/
public class ValidTimeActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private TextView startTime;          //开始时间
    private TextView stopTime;           //结束时间

    private RecyclerView reycycler;
    private ValidTimeAdapter adapter;
    private ArrayList<PlanTimeEntity> datas;


    public static SelectDayEntity startDay;
    public static SelectDayEntity stopDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_time);
        initVariables();
        initView();
        initEvent();
        initData();

        EventBus.getDefault().register(this);
    }

    private void initVariables() {
        Intent intent = getIntent();
        startDay = intent.getParcelableExtra("startDay");
        stopDay = intent.getParcelableExtra("stopDay");

    }

    private void initData() {

        datas = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;

        c.add(Calendar.MONTH,1);
        int nextYear = c.get(Calendar.YEAR);
        int nextMonth = c.get(Calendar.MONTH)+1;
        
        datas.add(new PlanTimeEntity(year,month));                //当前月份
        datas.add(new PlanTimeEntity(nextYear,nextMonth));        //下个月
        adapter = new ValidTimeAdapter(datas, ValidTimeActivity.this);
        reycycler.setAdapter(adapter);

    }

    private void initEvent() {
        back.setOnClickListener(this);
    }

    private void initView() {
        back = (ImageButton) findViewById(R.id.plan_time_back);
        startTime = (TextView) findViewById(R.id.plan_time_txt_start);
        stopTime = (TextView) findViewById(R.id.plan_time_txt_stop);

        if (stopDay.getDay() == -1) {
            stopTime.setText("结束"+"\n"+"时间");
        }else{
            stopTime.setText(stopDay.getMonth() + "月" + stopDay.getDay() + "日" );
        }
        if (startDay.getDay() == 0) {
            startTime.setText("结束"+"\n"+"时间");
        }else{
            startTime.setText(startDay.getMonth() + "月" + startDay.getDay() + "日" );
        }


        reycycler = (RecyclerView) findViewById(R.id.plan_time_calender);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,   // 上下文
                        LinearLayout.VERTICAL,  //垂直布局,
                        false);

        reycycler.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plan_time_back:
                Intent intent = new Intent();
                intent.putExtra("startDay",startDay);
                intent.putExtra("stopDay",stopDay);
                this.setResult(1, intent);
                finish();
                break;
        }
    }

    public void onEventMainThread(UpdataValidCalendar event) {
        adapter.notifyDataSetChanged();
        startTime.setText(startDay.getMonth()+"月"+startDay.getDay()+"日");
        if (stopDay.getDay() == -1) {
            stopTime.setText("结束"+"\n"+"时间");
        }else{
            stopTime.setText(stopDay.getMonth() + "月" + stopDay.getDay() + "日" );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
