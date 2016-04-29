package com.minkim.traveldiary;

import java.text.*;
import java.util.*;
import org.json.*;

public class Weather {
    private HashMap<String, String> map;
    private JSONObject i;
    private JSONObject j;
    public Weather(JSONObject i, JSONObject j){
        this.j = j;
        this.i = i;
        map = new HashMap<>();
        try {
            list();
        } catch (JSONException e) {
        }
    }

    public void list() throws JSONException {
        long unixDate = j.getLong("dt")*1000;
        SimpleDateFormat s  = new SimpleDateFormat("MMMM d, yyyy");
        s.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String date = s.format(unixDate);
        map.put("date", date);
        String city = i.getString("name");
        map.put("city", city);
        String hum = String.valueOf(j.getInt("humidity"));
        map.put("humidity", hum);
        JSONObject weather  = (JSONObject) j.getJSONArray("weather").get(0);
        String main = weather.getString("main");
        map.put("main", main);
        String desc = weather.getString("description");
        map.put("desc", desc);
        JSONObject temp = j.getJSONObject("temp");
        String min = String.valueOf(temp.getInt("min"));
        map.put("min", min);
        String max = String.valueOf(temp.getInt("max"));
        map.put("max", max);
    }

    public String getCity() {
        return map.get("city");
    }

    public String getDate() {
        return map.get("date");
    }

    public String getMain() {
        return map.get("main");
    }

    public String getMin() {
        return map.get("min");
    }

    public String getMax() {
        return map.get("max");
    }

    public String getDesc() {
        return map.get("desc");
    }

    public String getHum() {
        return map.get("humidity");
    }

}