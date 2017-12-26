package com.example.kevin.firebasetest4.FireBase;

/**
 * Created by kevin on 2017/12/1.
 */

public class TWzipCode {
    public String zipcode;
    public String city;
    public String road;
    public String roadNumber;

    public TWzipCode() {
    }

    public TWzipCode(String zipcode, String city, String road, String roadNumber) {
        this.zipcode = zipcode;
        this.city = city;
        this.road = road;
        this.roadNumber = roadNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    public String getRoad() {
        return road;
    }

    public String getRoadNumber() {
        return roadNumber;
    }
}
