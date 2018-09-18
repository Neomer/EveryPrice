package com.neomer.everyprice.api.models;

import java.sql.Driver;
import java.util.UUID;

public class Shop {

    private UUID Uid;
    private String Name;
    private String Address;
    private double Lat;
    private double Lng;

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
