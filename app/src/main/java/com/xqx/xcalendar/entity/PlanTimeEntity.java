package com.xqx.xcalendar.entity;

/**
 * Created by 徐启鑫 on 2017/1/17.
 */
public class PlanTimeEntity {
    private int year;
    private int month;

    public PlanTimeEntity(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
