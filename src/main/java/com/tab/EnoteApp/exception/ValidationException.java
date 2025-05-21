package com.tab.EnoteApp.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

    Map<String,Object> error = new LinkedHashMap<>();

    public ValidationException(Map<String, Object> error) {
        super("validation failed");
        this.error = error;
    }

    public Map<String, Object> getError() {
        return error;
    }
}
