package com.github.mzebib.provisioningapi.model.client;

/**
 * @author mzebib
 */
public class CreateUser extends UserInfo {
    private String password;

    public CreateUser() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
