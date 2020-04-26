package com.example.apod;

import java.util.Calendar;

public class APODDate {

    private Calendar c;

    public APODDate() {
        this.c = Calendar.getInstance();
    }

    public void setDate(int year, int month, int dayOfMonth) {
        this.c.set(year, month, dayOfMonth);
    }

    public void addDay() {
        this.c.add(Calendar.DATE, 1);
    }

    public void subtractDay() {
        this.c.add(Calendar.DATE, -1);
    }

    public String formatted() {
        int year = this.getYear();
        int month = this.getMonth();
        int dayOfMonth = this.getDayOfMonth();
        return String.format("%s-%s-%s", year, month, dayOfMonth);
    }

    public Calendar get() {
        return this.c;
    }

    public int getYear() {
        return this.c.get(Calendar.YEAR);
    }

    public int getMonth() {
        return this.c.get(Calendar.MONTH) + 1;
    }

    public int getDayOfMonth() {
        return this.c.get(Calendar.DAY_OF_MONTH);
    }

    public long getMaxDate() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public long getMinDate() {
        // Min date: June 16, 1995
        Calendar minDate = Calendar.getInstance();
        minDate.set(1995, 5, 16);
        return minDate.getTimeInMillis();
    }
}
