package com.minkim.traveldiary;

import java.io.Serializable;

/**
 * Created by roseanna on 4/29/16.
 */
public class City implements Serializable{
    private String city;
    private String country;

    public City(String city, String country){
        this.city = city;
        this.country = country;
    }

    public String getCity(){
        return city;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public void setCity(String city){
        this.city = city;
    }
}
