package com.speer.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private  String secretKey;
    private  Integer tokenExpiryMinutes;

    public JwtProperties() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getTokenExpiryMinutes() {
        return tokenExpiryMinutes;
    }

    public void setTokenExpiryMinutes(Integer tokenExpiryMinutes) {
        this.tokenExpiryMinutes = tokenExpiryMinutes;
    }
}