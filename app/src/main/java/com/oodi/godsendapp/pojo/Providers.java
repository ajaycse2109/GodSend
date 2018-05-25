package com.oodi.godsendapp.pojo;

import android.accessibilityservice.GestureDescription;

public class Providers {

    public String name;
    public String logo;
    public String providerid;


    public String getProviderid() {
        return providerid;
    }

    public void setProviderid(String providerid) {
        this.providerid = providerid;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Providers()
    {

    }
    public Providers(String Name,String Logo)
    {
       this.name = Name;
        this.logo = Logo;
    }

    public String get_hospitalName()
    {

        return name;
    }
}
