package com.github.mzebib.provisioningapi.model.client;

/**
 * @author mzebib
 */
public class TokenResponse implements Response {

    private String token;

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
