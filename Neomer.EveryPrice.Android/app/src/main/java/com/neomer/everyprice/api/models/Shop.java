package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Shop implements Parcelable {

    private UUID Uid;
    private String Name;
    private String Address;
    private double Lat;
    private double Lng;
    private List<Tag> Tags;

    protected Shop(Parcel in) {
        Uid = UUID.fromString(in.readString());
        Name = in.readString();
        Address = in.readString();
        Lat = in.readDouble();
        Lng = in.readDouble();
        Tags = new ArrayList<>();
        in.readTypedList(Tags, Tag.CREATOR);
    }

    public Shop(String name, String address, double lat, double lng, String tags) {
        Name = name;
        Address = address;
        Lat = lat;
        Lng = lng;
        if (tags != null)
        {
            Tags = new ArrayList<>();
            String[] strings = tags.split(" ");
            for (String s : strings) {
                Tags.add(new Tag(s));
            }
        }
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Uid.toString());
        parcel.writeString(Name);
        parcel.writeString(Address);
        parcel.writeDouble(Lat);
        parcel.writeDouble(Lng);
        parcel.writeTypedList(Tags);
    }

    public List<Tag> getTags() {
        return Tags;
    }

    public void setTags(List<Tag> tags) {
        Tags = tags;
    }
}
