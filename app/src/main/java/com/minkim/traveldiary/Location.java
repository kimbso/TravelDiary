package com.minkim.traveldiary;

import java.io.*;
import java.util.*;

/**
 * Created by roseanna on 4/29/16.
 */
public class Location implements Serializable{
    public String description;
    public ArrayList<String> pictures;
    private boolean selected;
    public String dates, location;
    public Location(String location){
        this.location       = location;
        this.description    = "";
        this.pictures       = null;
        this.dates          = null;
        this.selected       = false;
    }
    public Location(String location, String description, ArrayList pictures){
        this.location       = location;
        this.description    = description;
        this.pictures       = pictures;
        this.dates          = null;
        this.selected       = false;
    }

    public Location(String location, String description, ArrayList pictures, String dates){
        this.location       = location;
        this.description    = description;
        this.pictures       = pictures;
        this.selected       = false;
        this.dates          = dates;
    }

    public String getLocation(){
        return this.location;
    }
    public String getDescription(){
        return this.description;
    }
    public String getDates(){
        return this.dates;
    }
    public ArrayList getPictures(){
        return this.pictures;
    }
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected(){
        return selected;
    }
}
