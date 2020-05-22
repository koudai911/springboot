package com.study.verifydemo.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import net.sf.json.JSONObject;

public class ResultMsg {
    private int code;
    private String msg;
    private Object data;

    public ResultMsg() {
    }

    private ResultMsg(int code) {
        this.code = code;
    }

    private ResultMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMsg(ResultStatusCode status, Object data) {
        this.code = status.getCode();
        this.msg = status.getMsg();
        this.data = data;
    }

    public ResultMsg(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code == ResultStatusCode.OK.getCode();
    }

    public static ResultMsg createBySuccess() {
        return new ResultMsg(ResultStatusCode.OK.getCode());
    }

    public static ResultMsg createBySuccessMessage(String msg) {
        return new ResultMsg(ResultStatusCode.OK.getCode(), msg);
    }

    public static ResultMsg createBySuccess(Object data) {
        return new ResultMsg(ResultStatusCode.OK, data);
    }

    public static ResultMsg createBySuccess(String msg, Object data) {
        return new ResultMsg(ResultStatusCode.OK.getCode(), msg, data);
    }

    public static ResultMsg createByError() {
        return new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg());
    }

    public static ResultMsg createByErrorMessage(String errorMessage) {
        return new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), errorMessage);
    }

    public static ResultMsg createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ResultMsg(errorCode, errorMessage);
    }

    public static ResultMsg createByErrorCodeMessageData(int errorCode, String errorMessage, Object data) {
        return new ResultMsg(errorCode, errorMessage, data);
    }

    public String toString() {
        JSONObject jo = JSONObject.fromObject(this);
        return jo.toString();
    }
}