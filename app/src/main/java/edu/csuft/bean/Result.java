package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:55.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 包含了主要的数据
 */
public class Result implements Serializable{

    /** 城市名称 */
    private String city;
    /** 城市代码 */
    private String citycode;
    /** 当天时间 */
    private String date;
    /** 天气描述 */
    private String weather;
    /** 温度 */
    private String temp;
    /** 最高温度 */
    private String temphigh;
    /** 最低温度 */
    private String templow;
    /** 湿度 */
    private String humidity;
    /** 气压 */
    private String pressure;
    /** 风向 */
    private String winddirect;
    /** 风力 */
    private String windpower;
    /** 更新时间 */
    private String updatetime;

    /** 指数集合 */
    private ArrayList<Index> index;
    /** aqi */
    private Aqi aqi;
    /** 七天天气集合 */
    private ArrayList<Daily> daily;
    /** 24小时天气集合 */
    private ArrayList<Hourly> hourly;

    public Result(String city, String citycode, String date,
                  String weather, String temp, String temphigh,
                  String templow, String humidity, String pressure,
                  String winddirect, String windpower, String updatetime,
                  ArrayList<Index> index, Aqi aqi, ArrayList<Daily> daily,
                  ArrayList<Hourly> hourly) {
        this.city = city;
        this.citycode = citycode;
        this.date = date;
        this.weather = weather;
        this.temp = temp;
        this.temphigh = temphigh;
        this.templow = templow;
        this.humidity = humidity;
        this.pressure = pressure;
        this.winddirect = winddirect;
        this.windpower = windpower;
        this.updatetime = updatetime;
        this.index = index;
        this.aqi = aqi;
        this.daily = daily;
        this.hourly = hourly;
    }

    public Result() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWinddirect() {
        return winddirect;
    }

    public void setWinddirect(String winddirect) {
        this.winddirect = winddirect;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public ArrayList<Index> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Index> index) {
        this.index = index;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public ArrayList<Daily> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<Daily> daily) {
        this.daily = daily;
    }

    public ArrayList<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(ArrayList<Hourly> hourly) {
        this.hourly = hourly;
    }
}
