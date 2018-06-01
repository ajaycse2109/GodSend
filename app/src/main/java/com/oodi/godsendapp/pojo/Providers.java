package com.oodi.godsendapp.pojo;

import android.accessibilityservice.GestureDescription;

public class Providers {

    public String name;
    public String logo;
    public String providerid;
    public String lat;
    public String lon;
    public  String distance;
    public  String time;
    public  String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }



    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }



    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }



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
