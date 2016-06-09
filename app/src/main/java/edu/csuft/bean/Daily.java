package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:56.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 包含了未来7天数据信息
 */
public class Daily implements Serializable{

    /** 时间 */
    private String date;
    /** 星期 */
    private String week;
    /** 日出 */
    private String sunrise;
    /** 日落 */
    private String sunset;

    /** 夜晚情况 */
    private Night night;
    /** 白天情况 */
    private Day day;

    public Daily(String date, String week, String sunrise,
                 String sunset, Night night, Day day) {
        this.date = date;
        this.week = week;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.night = night;
        this.day = day;
    }

    public Daily() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}

