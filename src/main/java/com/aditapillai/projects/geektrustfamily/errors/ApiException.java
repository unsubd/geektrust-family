package com.aditapillai.projects.geektrustfamily.errors;

public class ApiException extends RuntimeException {
    private final String message;

    public ApiException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
