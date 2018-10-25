package com.neomer.everyprice.api.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Token {

    private UUID Token;
    private Date TokenExpirationDate;
    private UUID Uid;
    private String Username;

    public UUID getToken() {
        return Token;
    }

    public void setToken(UUID token) {
        Token = token;
    }

    public Date getTokenExpirationDate() {
        return TokenExpirationDate;
    }

    public void setTokenExpirationDate(Date tokenExpirationDate) {
        TokenExpirationDate = tokenExpirationDate;
    }

    public UUID getUid() {
        return Uid;
    }

    public void setUid(UUID uid) {
        Uid = uid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
