package com.iychay.be.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security.jwt")
public class JwtProperties {

    /**
     * Secret key used for signing JWT tokens. Configure through environment variables in each environment.
     */
    private String secret;

    /**
     * Token expiration in minutes.
     */
    private Long expirationMinutes;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpirationMinutes() {
        return expirationMinutes;
    }

    public void setExpirationMinutes(Long expirationMinutes) {
        this.expirationMinutes = expirationMinutes;
    }
}
