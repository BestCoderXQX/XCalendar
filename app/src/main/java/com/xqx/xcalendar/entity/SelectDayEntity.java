package com.xqx.xcalendar.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 徐启鑫 on 2017/1/17.
 * 选择日期 每一个单个天数的
 */
public class SelectDayEntity implements Parcelable {
    private int day ;           //日期
    private int month;           //属于的月份
    private int year;           //属于的年份

    private int monthPosition;    //属于的月份位置
    private int dayPosition;      //属于的日期位置

    public SelectDayEntity(int day, int month, int year, int monthPosition) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.monthPosition = monthPosition;
    }



    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public int getMonthPosition() {
        return monthPosition;
    }

    public void setMonthPosition(int monthPosition) {
        this.monthPosition = monthPosition;
    }

    public int getDayPosition() {
        return dayPosition;
    }

    public void setDayPosition(int dayPosition) {
        this.dayPosition = dayPosition;
    }

    @Override
    public String toString() {
        return "SelectDayEntity{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.day);
        dest.writeInt(this.month);
        dest.writeInt(this.year);
        dest.writeInt(this.monthPosition);
        dest.writeInt(this.dayPosition);
    }

    protected SelectDayEntity(Parcel in) {
        this.day = in.readInt();
        this.month = in.readInt();
        this.year = in.readInt();
        this.monthPosition = in.readInt();
        this.dayPosition = in.readInt();
    }

    public static final Creator<SelectDayEntity> CREATOR = new Creator<SelectDayEntity>() {
        @Override
        public SelectDayEntity createFromParcel(Parcel source) {
            return new SelectDayEntity(source);
        }

        @Override
        public SelectDayEntity[] newArray(int size) {
            return new SelectDayEntity[size];
        }
    };
}
