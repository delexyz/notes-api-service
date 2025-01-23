package com.speer.dto;

public class AuthUserResDto {
    private Boolean isAuthenticated;
    private String status;
    private String jwt;
    private UserResDto user;

    public AuthUserResDto() {}

    public AuthUserResDto(Boolean isAuthenticated, String status, String jwt, UserResDto user) {
        this.isAuthenticated = isAuthenticated;
        this.status = status;
        this.jwt = jwt;
        this.user = user;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto user) {
        this.user = user;
    }
}
