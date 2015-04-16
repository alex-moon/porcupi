package com.github.alex_moon.porcupi.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.Route;
import spark.Spark;

import com.github.alex_moon.porcupi.controllers.Controller;
import com.github.alex_moon.porcupi.handlers.PokeHandler;
import com.github.alex_moon.porcupi.handlers.Pokeable;
import com.github.alex_moon.porcupi.manager.ManagerServer;
import com.github.alex_moon.porcupi.responses.Response;
import com.google.gson.Gson;

public class View implements Pokeable {
    protected static Gson gson = new Gson();
    protected Object track;
    protected Controller controller;
    protected PokeHandler pokeHandler;
    protected List<String> poking = new ArrayList<String>();
    protected Map<String, Route> routes = new HashMap<String, Route>();
    protected String name = "bogus";
    
    public View(String name, Controller controller) {
        this.name = name;
        this.controller = controller;
        pokeHandler = new PokeHandler(this);
        ManagerServer.get().registerHandler(pokeHandler);
    }
    
    public String poke(List<String> tokens) {
        String routeToPoke = tokens.get(0);
        for (String routeFullName: routes.keySet()) {
            System.out.println("trying to poke " + routeFullName + " with " + routeToPoke);
            if (routeToPoke.equals(routeFullName)) {
                poking.add(routeFullName);
                return routeFullName;
            }
        }
        return null;
    }
    
    private void pokeHandle(String routeToPoke) {
        for (String routeFullName : poking) {
            if (routeToPoke.equals(routeFullName)) {
                ManagerServer.get().tell("now at " + routeToPoke + " - what would you like to do?");
                pokeHandler.activateContext(routeFullName);
            }
        }
    }
    
    public Object getTrack() {
        return track;
    }

    public void get(String urlPattern, String routeName, Route route) {
        String routeFullName = name + ":" + routeName;
        Spark.get(urlPattern, (request, response) -> {
            track = request;
            pokeHandle(routeFullName);
            Object result = route.handle(request, response);
            track = result;
            pokeHandle(routeFullName);
            return result;
        });
        routes.put(routeFullName, route);
    }

    public void post(String urlPattern, String routeName, Route route) {
        String routeFullName = name + ":" + routeName;
        Spark.post(urlPattern, (request, response) -> {
            track = request;
            pokeHandle(routeFullName);
            Object result = route.handle(request, response);
            track = result;
            pokeHandle(routeFullName);
            return result;
        });
        routes.put(routeFullName, route);
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
