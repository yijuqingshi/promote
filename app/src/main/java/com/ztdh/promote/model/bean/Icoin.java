package com.ztdh.promote.model.bean;

import java.io.Serializable;

public class Icoin implements Serializable {


    private static final long serialVersionUID = -7869554478931100189L;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String icon;

    private String name;

    private double  available;

    private double freeze;

    private  boolean canChongzhi;

    private boolean canTixian;

    private boolean canDingcun;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable(double available) {
        this.available = available;
    }

    public double getFreeze() {
        return freeze;
    }

    public void setFreeze(double freeze) {
        this.freeze = freeze;
    }

    public boolean isCanChongzhi() {
        return canChongzhi;
    }

    public void setCanChongzhi(boolean canChongzhi) {
        this.canChongzhi = canChongzhi;
    }

    public boolean isCanTixian() {
        return canTixian;
    }

    public void setCanTixian(boolean canTixian) {
        this.canTixian = canTixian;
    }

    public boolean isCanDingcun() {
        return canDingcun;
    }

    public void setCanDingcun(boolean canDingcun) {
        this.canDingcun = canDingcun;
    }
}
