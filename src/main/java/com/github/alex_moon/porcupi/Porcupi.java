package com.github.alex_moon.porcupi;

import spark.Spark;

class Porcupi {
    public static void main(String[] args) {
        Spark.get("/", (request, response) -> {
            return "You doed it lol";
        });
    }
}
