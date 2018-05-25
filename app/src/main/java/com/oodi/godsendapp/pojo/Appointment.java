package com.oodi.godsendapp.pojo;

import java.io.Serializable;

/**
 * Created by pc on 3/14/18.
 */

public class Appointment implements Serializable {


    public  String appCode;
    public String provName;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }



    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }


}
