package com.minkim.traveldiary;

import java.util.ArrayList;

/**
 * Created by roseanna on 4/29/16.
 */
public class Location {
    public City city;
    public String description;
    public ArrayList<String> favoritePlaces;
    public ArrayList<String> pictures;

    public Location(City city, String description, ArrayList favoritePlaces, ArrayList pictures){
        this.city           = city;
        this.description    = description;
        this.favoritePlaces = favoritePlaces;
        this.pictures       = pictures;
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
}
