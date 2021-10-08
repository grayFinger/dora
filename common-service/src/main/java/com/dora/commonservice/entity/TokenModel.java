package com.dora.commonservice.entity;

import java.io.Serializable;
import java.time.Instant;

public class TokenModel implements Serializable {

    private static final long serialVersionUID = -2126466461380912711L;
    private UserAuthorityVO user;
    private String token;
    private String refreshToken;

    public UserAuthorityVO getUser() {
        return user;
    }

    public void setUser(UserAuthorityVO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
