package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:56.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 包含了一天中24小时天气数据信息
 */
public class Hourly implements Serializable{

    /** 小时时间 */
    private String time;
    /** 天气 */
    private String weather;
    /** 温度 */
    private String temp;

    public Hourly(String time, String weather, String temp) {
        this.time = time;
        this.weather = weather;
        this.temp = temp;
    }

    public Hourly() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
