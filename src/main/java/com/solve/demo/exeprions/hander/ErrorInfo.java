package com.solve.demo.exeprions.hander;

import org.springframework.http.HttpStatus;

public class ErrorInfo {
    private final HttpStatus status;
    private final Class exeprionClass;
    private final String message;

    public ErrorInfo(HttpStatus status, Class exeprionClass, String message) {
        this.status = status;
        this.exeprionClass = exeprionClass;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Class getExeprionClass() {
        return exeprionClass;
    }

    public String getMessage() {
        return message;
    }
}
