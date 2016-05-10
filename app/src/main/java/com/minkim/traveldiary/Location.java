package com.minkim.traveldiary;

import java.io.*;
import java.util.*;

/**
 * Created by roseanna on 4/29/16.
 */
public class Location implements Serializable{
    public City city;
    public String description;
    public ArrayList<String> pictures;
    private boolean selected;
    public String dates;
    public Location(City city){
        this.city           = city;
        this.description    = "";
        this.pictures       = null;
        this.dates          = null;
        this.selected       = false;
    }
    public Location(City city, String description, ArrayList pictures){
        this.city           = city;
        this.description    = description;
        this.pictures       = pictures;
        this.dates          = null;
        this.selected       = false;
    }

    public Location(City city, String description, ArrayList pictures, String dates){
        this.city           = city;
        this.description    = description;
        this.pictures       = pictures;
        this.selected       = false;
        this.dates          = dates;
    }

    public City getCity(){
        return this.city;
    }

    public String getDescription(){
        return this.description;
    }

    public ArrayList getPictures(){
        return this.pictures;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getDates(){
        return this.dates;
    }
    public boolean isSelected(){
        return selected;
    }
}
