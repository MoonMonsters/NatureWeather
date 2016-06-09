package edu.csuft.bean;

import java.io.Serializable;

/**
 * Created by Chalmers on 2016-06-05 19:43.
 * email:qxinhai@yeah.net
 */
public class SimpleCity implements Serializable {

    /**
     * 城市名称
     */
    private String city;
    /**
     * 城市编号
     */
    private String citycode;

    public SimpleCity(String city, String citycode) {
        this.city = city;
        this.citycode = citycode;
    }

    public SimpleCity() {
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

    @Override
    public String toString() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleCity that = (SimpleCity) o;

        return city != null ? city.equals(that.city) : that.city == null;

    }

    @Override
    public int hashCode() {
        return city != null ? city.hashCode() : 0;
    }
}
