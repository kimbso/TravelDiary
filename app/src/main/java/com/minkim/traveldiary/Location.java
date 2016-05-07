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

    public Location(City city){
        this.city = city;
        this.description = "";
        this.favoritePlaces = null;
        this.pictures = null;
    }
    public Location(City city, String description, ArrayList favoritePlaces, ArrayList pictures){
        this.city           = city;
        this.description    = description;
        this.favoritePlaces = favoritePlaces;
        this.pictures       = pictures;
        this.selected = false;
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

    public boolean isSelected(){
        return selected;
    }
}
