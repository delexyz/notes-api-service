package com.speer.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class AuthUserResDto {
    private Boolean isAuthenticated;
    private String status;
    private String jwt;
    private UserResDto user;
}
