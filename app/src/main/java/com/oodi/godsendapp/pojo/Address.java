package com.oodi.godsendapp.pojo;

import java.io.Serializable;

/**
 * Created by pc on 3/15/18.
 */

public class Address implements Serializable {

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String address;
    public String phone;
}
