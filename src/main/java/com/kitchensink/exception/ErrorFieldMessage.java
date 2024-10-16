package com.kitchensink.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorFieldMessage {
    private int code;
    private String message;
    Map<String,String> errors = new HashMap<>();
    private Date timestamp;
}