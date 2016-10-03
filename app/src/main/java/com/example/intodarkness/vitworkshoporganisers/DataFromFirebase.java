package com.example.intodarkness.vitworkshoporganisers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by nandu on 29-04-2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataFromFirebase {

    private String rollNo;
    public DataFromFirebase() {
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }



    public String getRollNo() {
        return rollNo;
    }


}
