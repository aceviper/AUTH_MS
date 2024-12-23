package com.iwomi.authms.core.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class GlobalResponse {

    public static ResponseEntity<Object> responseBuilder(
            String message, HttpStatus httpStatus, int success, Object responseObject
    ) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("httpStatus", httpStatus);
        response.put("success", success);
        response.put("data", responseObject);

        return new ResponseEntity<>(response, httpStatus);
    }

}


