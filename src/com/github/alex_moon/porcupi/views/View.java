package com.github.alex_moon.porcupi.views;

import spark.Route;
import spark.Spark;

import com.github.alex_moon.porcupi.responses.Response;
import com.google.gson.Gson;

public class View {
    protected static Gson gson = new Gson();

    public void get(String route, String name, Route method) {
        Spark.get(route, (request, response) -> {
            System.out.println("pre-get poke point for " + name);
            Object result = method.handle(request, response);
            System.out.println("post-get poke point for " + name);
            return result;
        });
    }

    public void post(String route, String name, Route method) {
        Spark.post(route, (request, response) -> {
            System.out.println("pre-post poke point for " + name);
            Object result = method.handle(request, response);
            System.out.println("post-post poke point for " + name);
            return result;
        });
    }
    
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
