package com.xqx.xcalendar.editValidTime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xqx.xcalendar.R;
import com.xqx.xcalendar.entity.PlanTimeEntity;
import com.xqx.xcalendar.entity.SelectDayEntity;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by 徐启鑫 on 2017/1/17.
 */
/*
* @author xqx
* @emil djlxqx@163.com
* create at 2017/4/20
* description: 选择方案有效期界面
*/
public class ValidTimeAdapter extends RecyclerView.Adapter<ValidTimeViewHolder>{

    private ArrayList<PlanTimeEntity> datas;
    private Context context;

    public ValidTimeAdapter(ArrayList<PlanTimeEntity> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }


    @Override
    public ValidTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ValidTimeViewHolder ret = null;
        // 不需要检查是否复用，因为只要进入此方法，必然没有复用
        // 因为RecyclerView 通过Holder检查复用
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_timeplan, parent, false);
        ret = new ValidTimeViewHolder(v,context);
        return ret;
    }

    @Override
    public void onBindViewHolder(ValidTimeViewHolder holder, int position) {
        PlanTimeEntity planTimeEntity = datas.get(position);
        holder.plan_time_txt_month.setText(planTimeEntity.getYear()+"--"+planTimeEntity.getMonth());
        //得到该月份的第一天
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, planTimeEntity.getYear());          //指定年份
        calendar.set(Calendar.MONTH, planTimeEntity.getMonth() - 1);        //指定月份 Java月份从0开始算
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        ArrayList<SelectDayEntity> days = new ArrayList<SelectDayEntity>();
        for (int i = 0; i < dayOfWeek-1; i++) {
            days.add(new SelectDayEntity(0,planTimeEntity.getMonth(),planTimeEntity.getYear(),position));
        }
        calendar.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        calendar.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        for (int i = 1; i <= calendar.get(Calendar.DAY_OF_MONTH); i++) {
            days.add(new SelectDayEntity(i,planTimeEntity.getMonth(),planTimeEntity.getYear(),position));
        }

        SelectValidTimeAdapter adapter = new SelectValidTimeAdapter(days,context);
        adapter.setAllowTime(7);  //设置允许选择的最后一天和起始日期相差的天数
        holder.plan_time_recycler_content.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (datas!=null){
            ret = datas.size();
        }
        return ret;
    }
}
