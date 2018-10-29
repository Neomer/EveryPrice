package com.neomer.everyprice.api.models;

public class UserSignInModel {

    public String Username;

    public String EncryptedPassword;

    public UserSignInModel(String username, String password) {
        Username = username;
        EncryptedPassword = password;
    }

}
