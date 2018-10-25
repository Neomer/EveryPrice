package com.neomer.everyprice.api.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Tag implements Parcelable {

    private UUID Uid;
    private String Value;

    public Tag(UUID uid, String value) {
        Uid = uid;
        Value = value;
    }

    public Tag(String value) {
        Value = value;
    }

    protected Tag(Parcel in) {
        Uid = UUID.fromString(in.readString());
        Value = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Uid.toString());
        dest.writeString(Value);
    }

    public UUID getUid() { return Uid;}
    public void setUid(UUID uid) { Uid = uid; }

    public String getValue() { return Value; }
    public void setValue(String value) { Value = value; }
}
