package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.neomer.everyprice.core.NumericHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product implements Parcelable {

    private UUID Uid;
    private String Name;
    private Price Price;

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        this.Uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    protected Product(Parcel in) {
        Uid = UUID.fromString(in.readString());
        Name = in.readString();
    }

    public Product(String name, double price) {
        Name = name;
        Price = new Price(price, "");
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
        dest.writeString(Uid.toString());
        dest.writeString(Name);
    }

    public com.neomer.everyprice.api.models.Price getPrice() {
        return Price;
    }

    public void setPrice(com.neomer.everyprice.api.models.Price price) {
        Price = price;
    }
}
