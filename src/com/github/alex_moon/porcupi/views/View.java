package com.github.alex_moon.porcupi.views;

import com.github.alex_moon.porcupi.responses.Response;
import com.google.gson.Gson;

public class View {
    protected static Gson gson = new Gson();

    // json response stuff
    public String success(Object object) {
        Response response = Response.getSuccessResponse();
        response.setObject(object);
        return gson.toJson(response);
    }

    public String error(String message) {
        Response response = Response.getErrorResponse();
        response.setMessage(message);
        return gson.toJson(response);
    }
}
