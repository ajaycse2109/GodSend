package com.oodi.godsendapp.pojo;

import java.io.Serializable;

/**
 * Created by pc on 3/14/18.
 */

public class Records {
        String fname, recordtype, description, recordfile;

        public Records(String fname, String recordtype, String description, String recordfile) {
            this.fname = fname;
            this.recordtype = recordtype;
            this.description = description;
            this.recordfile = recordfile;
        }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecordfile() {
        return recordfile;
    }

    public void setRecordfile(String recordfile) {
        this.recordfile = recordfile;
    }
}
