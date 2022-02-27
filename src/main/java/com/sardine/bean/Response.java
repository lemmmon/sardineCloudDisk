package com.sardine.bean;

import java.io.Serializable;

public class Response implements Serializable {
    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public static Response success() {
        Response response = new Response();
        response.setCode(0);
        return response;
    }

    public static Response success(Object data) {
        Response response = new Response();
        response.setData(data);
        response.setCode(0);
        return response;
    }

    public static Response fail(String message) {
        Response response = new Response();
        response.setCode(1);
        response.setMessage(message);
        return response;
    }
}

