package com.rodaxsoft.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {

    private String email;
    private String name;
    @JsonProperty("avatar_url")
    private String AvatarUrl;

    public Profile(){}

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getAvatarUrl(){
        return AvatarUrl;
    }

    public void setAvatarUrl(String AvatarUrl){
        this.AvatarUrl=AvatarUrl;
    }
}
