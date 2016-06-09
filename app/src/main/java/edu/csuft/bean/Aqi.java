package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:55.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 包含了所有的aqi数据信息
 */
public class Aqi implements Serializable{

    /** aqi的值 */
    private String aqi;
    /** aqi值的评价 */
    private String quality;
    /** 详细信息 */
    private AqiInfo aqiinfo;

    public Aqi(String aqi, String quality, AqiInfo aqiinfo) {
        this.aqi = aqi;
        this.quality = quality;
        this.aqiinfo = aqiinfo;
    }

    public Aqi() {
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public AqiInfo getAqiinfo() {
        return aqiinfo;
    }

    public void setAqiinfo(AqiInfo aqiinfo) {
        this.aqiinfo = aqiinfo;
    }
}

