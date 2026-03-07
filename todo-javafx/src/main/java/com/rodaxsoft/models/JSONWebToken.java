package com.rodaxsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONWebToken {
    @JsonProperty("jwt")
    private String jwt;

    @JsonProperty("refresh_token")
    private String refresh_token;

    public JSONWebToken() {}

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRefresh_Token() {
        return refresh_token;
    }

    public void setRefresh_Token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}