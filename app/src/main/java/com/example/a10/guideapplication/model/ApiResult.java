package com.example.a10.guideapplication.model;

public class ApiResult {

    private boolean StatusCode;
    private String Message;

    public boolean isStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(boolean statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
