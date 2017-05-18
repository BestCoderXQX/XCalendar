package com.xqx.xcalendar.editValidTime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.xqx.xcalendar.CalendarUtil;
import com.xqx.xcalendar.R;
import com.xqx.xcalendar.UpdataValidCalendar;
import com.xqx.xcalendar.ValidTimeActivity;
import com.xqx.xcalendar.entity.SelectDayEntity;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


/**
 * Created by 徐启鑫 on 2017/1/17.
 */
public class SelectValidTimeAdapter extends RecyclerView.Adapter<SelectValidTimeViewHolder>{

    private ArrayList<SelectDayEntity> days;
    private Context context;

    private int allowTime = 0;  //允许选择的结束时间和开始时间相差的天数

    public SelectValidTimeAdapter(ArrayList<SelectDayEntity> days, Context context) {
        this.days = days;
        this.context = context;

    }

    public void setAllowTime(int allowTime) {
        this.allowTime = allowTime-1;
    }

    @Override
    public SelectValidTimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SelectValidTimeViewHolder ret = null;
        // 不需要检查是否复用，因为只要进入此方法，必然没有复用
        // 因为RecyclerView 通过Holder检查复用
        View v = LayoutInflater.from(context).inflate(R.layout.item_recycler_selectday, parent, false);
        ret = new SelectValidTimeViewHolder(v);

        return ret;
    }

    @Override
    public void onBindViewHolder(SelectValidTimeViewHolder holder, final int position) {
        final SelectDayEntity selectDayEntity = days.get(position);
        //显示日期
        if (selectDayEntity.getDay()!=0) {
            holder.select_txt_day.setText(selectDayEntity.getDay() + "");
            holder.select_ly_day.setEnabled(true);
        }else{
            holder.select_ly_day.setEnabled(false);
        }
        holder.select_ly_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选出点击的日期和开始日期的间隔天数
                String twoDay = CalendarUtil.getTwoDay(selectDayEntity.getYear() + "-" + selectDayEntity.getMonth() + "-" + selectDayEntity.getDay(),
                        ValidTimeActivity.startDay.getYear() + "-" + ValidTimeActivity.startDay.getMonth() + "-" + ValidTimeActivity.startDay.getDay());
                int gapDate = Integer.parseInt(twoDay);

                if (gapDate>allowTime){
                    Toast.makeText(context,"结束日期距离开始日期不能超过"+(allowTime+1)+"天",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectDayEntity.getYear() > ValidTimeActivity.startDay.getYear()) {
                    //如果选中的年份大于开始的年份
                    ValidTimeActivity.stopDay.setDay(selectDayEntity.getDay());
                    ValidTimeActivity.stopDay.setMonth(selectDayEntity.getMonth());
                    ValidTimeActivity.stopDay.setYear(selectDayEntity.getYear());
                    ValidTimeActivity.stopDay.setMonthPosition(selectDayEntity.getMonthPosition());
                    ValidTimeActivity.stopDay.setDayPosition(position);
                } else if (selectDayEntity.getYear() == ValidTimeActivity.startDay.getYear()) {
                    //如果选中的年份 等于 选中的年份
                    if (selectDayEntity.getMonth() > ValidTimeActivity.startDay.getMonth()) {
                        ValidTimeActivity.stopDay.setDay(selectDayEntity.getDay());
                        ValidTimeActivity.stopDay.setMonth(selectDayEntity.getMonth());
                        ValidTimeActivity.stopDay.setYear(selectDayEntity.getYear());
                        ValidTimeActivity.stopDay.setMonthPosition(selectDayEntity.getMonthPosition());
                        ValidTimeActivity.stopDay.setDayPosition(position);
                    } else if (selectDayEntity.getMonth() == ValidTimeActivity.startDay.getMonth()) {
                        //月份 相等
                        if (selectDayEntity.getDay() >= ValidTimeActivity.startDay.getDay()) {
                            ValidTimeActivity.stopDay.setDay(selectDayEntity.getDay());
                            ValidTimeActivity.stopDay.setMonth(selectDayEntity.getMonth());
                            ValidTimeActivity.stopDay.setYear(selectDayEntity.getYear());
                            ValidTimeActivity.stopDay.setMonthPosition(selectDayEntity.getMonthPosition());
                            ValidTimeActivity.stopDay.setDayPosition(position);
                        } else {
                        }
                    } else {
                    }
                } else {
                }
                EventBus.getDefault().post(new UpdataValidCalendar());
            }
        });


        if (ValidTimeActivity.startDay.getYear()==selectDayEntity.getYear() && ValidTimeActivity.startDay.getMonth() == selectDayEntity.getMonth() && ValidTimeActivity.startDay.getDay() == selectDayEntity.getDay()
                &&ValidTimeActivity.stopDay.getYear()==selectDayEntity.getYear() && ValidTimeActivity.stopDay.getMonth() == selectDayEntity.getMonth() && ValidTimeActivity.stopDay.getDay() == selectDayEntity.getDay() ){
            //开始和结束同一天
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_startstop);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }
        else if (ValidTimeActivity.startDay.getYear()==selectDayEntity.getYear() && ValidTimeActivity.startDay.getMonth() == selectDayEntity.getMonth() && ValidTimeActivity.startDay.getDay() == selectDayEntity.getDay()){
            //开始点
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_start);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }else if(ValidTimeActivity.stopDay.getYear()==selectDayEntity.getYear() && ValidTimeActivity.stopDay.getMonth() == selectDayEntity.getMonth() && ValidTimeActivity.stopDay.getDay() == selectDayEntity.getDay()){
            //结束点
            holder.select_ly_day.setBackgroundResource(R.drawable.bg_time_stop);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
        }else if(selectDayEntity.getMonthPosition()>=ValidTimeActivity.startDay.getMonthPosition() && selectDayEntity.getMonthPosition()<=ValidTimeActivity.stopDay.getMonthPosition()){
            //处于开始和结束之间的点
            if (selectDayEntity.getMonthPosition()==ValidTimeActivity.startDay.getMonthPosition()&&selectDayEntity.getMonthPosition()==ValidTimeActivity.stopDay.getMonthPosition()){
                //开始和结束是一个月份
                if (selectDayEntity.getDay()>ValidTimeActivity.startDay.getDay() && selectDayEntity.getDay() <ValidTimeActivity.stopDay.getDay()) {
                    holder.select_ly_day.setBackgroundResource(R.color.standard_blue_1);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));
                }else{
                    holder.select_ly_day.setBackgroundResource(R.color.white);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
                }
            }else if(ValidTimeActivity.startDay.getMonthPosition() != ValidTimeActivity.stopDay.getMonthPosition()){
                // 日期和 开始 不是一个月份
                if (selectDayEntity.getMonthPosition()==ValidTimeActivity.startDay.getMonthPosition() && selectDayEntity.getDay()>ValidTimeActivity.startDay.getDay()){
                    //和初始相同月  天数往后
                    holder.select_ly_day.setBackgroundResource(R.color.standard_blue_1);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));

                }else if(selectDayEntity.getMonthPosition()==ValidTimeActivity.stopDay.getMonthPosition() && selectDayEntity.getDay()<ValidTimeActivity.stopDay.getDay()){
                    //和结束相同月   天数往前
                    holder.select_ly_day.setBackgroundResource(R.color.standard_blue_1);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));

                }else if(selectDayEntity.getMonthPosition()!=ValidTimeActivity.startDay.getMonthPosition() && selectDayEntity.getMonthPosition()!=ValidTimeActivity.stopDay.getMonthPosition()){
                    //和 开始结束都不是同一个月
                    holder.select_ly_day.setBackgroundResource(R.color.standard_blue_1);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.white));

                }else{
                    holder.select_ly_day.setBackgroundResource(R.color.white);
                    holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));

                }
            }

        }else{
            holder.select_ly_day.setBackgroundResource(R.color.white);
            holder.select_txt_day.setTextColor(context.getResources().getColor(R.color.txtColor));
        }

    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (days!=null){
            ret = days.size();
        }
        return ret;
    }
}
