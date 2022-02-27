package com.sardine.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, String> handler(Exception e) {
        Map<String, String> map = new HashMap<>();
        if (e instanceof CustomException) {
            CustomException customException = (CustomException) e;
            map.put("code", String.valueOf(customException.getCode()));
            map.put("message", customException.getMessage());
        } else {
            map.put("1", "internal exception");
        }
        return map;
    }
}
