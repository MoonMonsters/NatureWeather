package edu.csuft.bean;

import java.io.Serializable;

/**
 * 晚上天气情况
 */
public class Night implements Serializable {
    /** 天气情况 */
    private String weather;
    /** 最低温度 */
    private String templow;
    /** 风向 */
    private String winddirect;
    /** 风力 */
    private String windpower;

    public Night(String weather, String templow, String winddirect, String windpower) {
        this.weather = weather;
        this.templow = templow;
        this.winddirect = winddirect;
        this.windpower = windpower;
    }

    public Night(){}

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
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
}
