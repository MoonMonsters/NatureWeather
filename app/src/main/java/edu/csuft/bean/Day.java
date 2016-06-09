package edu.csuft.bean;

import java.io.Serializable;

/**
 * 白天天气情况
 */
public class Day implements Serializable {
    /** 天气情况 */
    private String weather;
    /** 最高温度 */
    private String temphigh;
    /** 风向 */
    private String winddirect;
    /** 风力 */
    private String windpower;

    public Day(String weather, String temphigh, String winddirect, String windpower) {
        this.weather = weather;
        this.temphigh = temphigh;
        this.winddirect = winddirect;
        this.windpower = windpower;
    }

    public Day() {
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
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
