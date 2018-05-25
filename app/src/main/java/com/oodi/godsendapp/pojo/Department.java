package com.oodi.godsendapp.pojo;

import java.io.Serializable;

/**
 * Created by pc on 3/13/18.
 */

public class Department implements Serializable {
    public Department(){}

    String id ;
    String name ;
    String service_type ;
    String department ;
    String short_description ;
    String image ;
    String price_description;
    String price ;
    String service_schedule;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice_description() {
        return price_description;
    }

    public void setPrice_description(String price_description) {
        this.price_description = price_description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getService_schedule() {
        return service_schedule;
    }

    public void setService_schedule(String service_schedule) {
        this.service_schedule = service_schedule;
    }
}
