package com.neomer.everyprice.api.models;

import java.util.UUID;

public class TagFastSearchViewModel {

    private UUID Uid;
    private String Value;

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        Uid = uid;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}