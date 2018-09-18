package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Product implements Parcelable {

    private UUID uid;
    private String name;
    private double price;

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    protected Product(Parcel in) {
        uid = UUID.fromString(in.readString());
        name = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid.toString());
        dest.writeString(name);
        dest.writeDouble(price);
    }
}
