package com.rudykart.person.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ValidateResponse {
    
    private String message;
    private int status;
    private Map<String, List<String>> errors;
}
