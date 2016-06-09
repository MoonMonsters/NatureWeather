package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:55.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 包含了所有的指数数据信息
 */
public class Index implements Serializable{

    /** 指数名称 */
    private String iname;
    /** 指数简述 */
    private String ivalue;
    /** 指数详细信息 */
    private String detail;

    public Index(String iname, String ivalue, String detail) {
        this.iname = iname;
        this.ivalue = ivalue;
        this.detail = detail;
    }

    public Index() {
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIvalue() {
        return ivalue;
    }

    public void setIvalue(String ivalue) {
        this.ivalue = ivalue;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
