package com.speer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private Boolean isSuccessful;
    private String message;
    private Object data;
}