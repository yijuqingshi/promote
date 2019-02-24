package com.ztdh.promote.model.bean;

public class TypeUtils {



    public static String getType(String value){
        switch (value){
            case "1":

             return "注册赠送";

            case "2":

                return "利润分成";
            case "3":

                return "提币";
            case "4":

                return "充币";
            case "5":

                return "推广赠送";
            case "6":

                return "后台操作";
            case "7":

                return "购买理财产品";
            case "8":

                return "锁仓释放";
        }
        return "";
    }


}
