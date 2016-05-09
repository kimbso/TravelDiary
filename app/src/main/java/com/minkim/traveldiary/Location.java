package com.minkim.traveldiary;

import java.io.*;
import java.util.*;

/**
 * Created by roseanna on 4/29/16.
 */
public class Location implements Serializable{
    public City city;
    public String description;
    public ArrayList<String> favoritePlaces;
    public ArrayList<String> pictures;
    private boolean selected;
    public String dates;
    public Location(City city){
        this.city           = city;
        this.description    = "";
        this.favoritePlaces = null;
        this.pictures       = null;
        this.dates          = null;
        this.selected       = false;
    }
    public Location(City city, String description, ArrayList favoritePlaces, ArrayList pictures){
        this.city           = city;
        this.description    = description;
        this.favoritePlaces = favoritePlaces;
        this.pictures       = pictures;
        this.dates          = null;
        this.selected       = false;
    }

    public Location(City city, String description, ArrayList favoritePlaces, ArrayList pictures, String dates){
        this.city           = city;
        this.description    = description;
        this.favoritePlaces = favoritePlaces;
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

    public ArrayList getFavoritePlaces(){
        return this.favoritePlaces;
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

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }
}
