package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Price implements Parcelable {

    private UUID Uid;
    private double Value;
    private Date CreationDate;
    private String Unit;

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        this.Uid = uid;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        this.Value = value;
    }
/*
    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.CreationDate = creationDate;
    }
*/
    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        this.Unit = unit;
    }

    protected Price(Parcel in) {
        Uid = UUID.fromString(in.readString());
        Value = in.readDouble();
        /*
        try {
            CreationDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        Unit = in.readString();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uid.toString());
        dest.writeDouble(Value);
        //dest.writeString(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").format(CreationDate));
        dest.writeString(Unit);
    }
}
