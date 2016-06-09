package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-02 21:09.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 定位时需要用到的城市类
 */
public class City implements Serializable{

    /** 二级城市 */
    private String name_cn;
    /** 一级城市 */
    private String district_cn;
    /** 省份 */
    private String province_cn;
    /** 二级城市拼音 */
    private String name_en;
    /** 编号，所有api通用 */
    private String area_id;

    public City(String province_cn, String district_cn,
                String name_cn, String name_en, String area_id) {
        this.province_cn = province_cn;
        this.district_cn = district_cn;
        this.name_cn = name_cn;
        this.name_en = name_en;
        this.area_id = area_id;
    }

    public City(){}

    public String getProvince_cn() {
        return province_cn;
    }

    public void setProvince_cn(String province_cn) {
        this.province_cn = province_cn;
    }

    public String getDistrict_cn() {
        return district_cn;
    }

    public void setDistrict_cn(String district_cn) {
        this.district_cn = district_cn;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    @Override
    public String toString() {
        return name_cn + ',' + district_cn + ',' + province_cn;
    }

}
