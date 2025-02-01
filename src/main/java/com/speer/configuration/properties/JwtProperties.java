package com.speer.configuration.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private  String secretKey;
    private  Integer tokenExpiryMinutes;
}