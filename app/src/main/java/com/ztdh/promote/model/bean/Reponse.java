package com.ztdh.promote.model.bean;

public class Reponse<T> {

    private String status;

    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;

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


}
