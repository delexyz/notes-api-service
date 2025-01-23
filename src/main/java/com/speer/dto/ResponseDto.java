package com.speer.dto;



public class ResponseDto {
    private Boolean isSuccessful;
    private String message;
    private Object data;

    public ResponseDto() {
    }

    public ResponseDto(Boolean isSuccessful, String message, Object data) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}