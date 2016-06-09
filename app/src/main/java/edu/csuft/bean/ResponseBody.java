package edu.csuft.bean;

/**
 * Created by Chalmers on 2016-06-03 18:53.
 * email:qxinhai@yeah.net
 */

import java.io.Serializable;

/**
 * 接收的天气数据主体，包含了多余的数据在里面，主要是为了符合json格式才添加的这个类
 */
public class ResponseBody implements Serializable{

    /** 状态描述 */
    private String status;
    /** 描述信息 */
    private String msg;
    /** 消息主体 */
    private Result result;

    public ResponseBody(String status, String msg, Result result) {
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public ResponseBody(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
