package com.neomer.everyprice.api.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Token {

    private UUID Token;
    private  LocalDateTime TokenExpirationDate;

    public UUID getToken() {
        return Token;
    }

    public void setToken(UUID token) {
        Token = token;
    }

    public LocalDateTime getTokenExpirationDate() {
        return TokenExpirationDate;
    }

    public void setTokenExpirationDate(LocalDateTime tokenExpirationDate) {
        TokenExpirationDate = tokenExpirationDate;
    }
}
