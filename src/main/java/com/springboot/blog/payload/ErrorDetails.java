package com.springboot.blog.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestap;
    private String message;
    private String details;

    public ErrorDetails(Date timestap, String message, String details) {
        this.timestap = timestap;
        this.message = message;
        this.details = details;
    }

    public Date getTimestap() {
        return timestap;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
