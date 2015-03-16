package com.github.alex_moon.porcupi.responses;

public class Response {
    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private String status;
    private String message;
    
    public static Response getSuccessResponse() {
        Response response = new Response();
        response.status = STATUS_SUCCESS;
        return response;
    }

    public static Response getErrorResponse() {
        Response response = new Response();
        response.status = STATUS_ERROR;
        return response;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    private Object object;
}
