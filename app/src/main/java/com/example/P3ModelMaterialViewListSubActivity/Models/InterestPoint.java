package com.example.P3ModelMaterialViewListSubActivity.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("InterestPoint")
public class InterestPoint extends ParseObject {

    public InterestPoint() {

    }

    public Integer getId() {
        return getInt("id");
    }

    public void setId(Integer id) {
        put("id", id);
    }


    public Double getLatitude() {
        return getDouble("latitude");
    }

    public void setLatitude(Double latitude) {
        put("latitude", latitude);
    }


    public Double getLongitude() {
        return getDouble("longitude");
    }

    public void setLongitude(Double longitude) {
        put("longitude", longitude);
    }


    public Integer getParkingN() {
        return getInt("parkingN");
    }

    public void setParkingN(Integer parkingN) {
        put("parkingN", parkingN);
    }

    public String getParking() {
        return getString("parking");
    }

    public void setParking(String parking) {
        put("parking", parking);
    }


    public Integer getBattery() {
        return getInt("battery");
    }

    public void setBattery(Integer battery) {
        put("battery", battery);
    }


    public float getRemainingTime() {
        return (float) getDouble("remainingTime");
    }

    public void setRemainingTime(float remainingTime) {
        put("remainingTime", (double) remainingTime);
    }

    public float getTemperature() {
        return (float) getDouble("temperature");
    }

    public void setTemperature(float temperature) {
        put("temperature", (double) temperature);
    }


    public Boolean getChargerConnected() {
        return getBoolean("chargerConnected");
    }

    public void setChargerConnected(Boolean chargerConnected) {
        put("chargerConnected", chargerConnected);
    }


    public float getPressure() {
        return (float) getDouble("pressure");
    }

    public void setPressure(float pressure) {
        put("pressure", (double) pressure);
    }


    public Boolean getInflationRecommendation() {
        return getBoolean("inflationRecommendation");
    }

    public void setInflationRecommendation(Boolean inflationRecommendation) {
        put("inflationRecommendation", inflationRecommendation);
    }


    public String getDate() {
        return getString("date");
    }

    public void setDate(String date) {
        put("date", date);
    }

    public String getRepair() {
        return getString("repair");
    }

    public void setRepair(String repair) {
        put("repair", repair);
    }
}
