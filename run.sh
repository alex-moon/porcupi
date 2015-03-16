#!/bin/bash
mvn clean package; java -jar $(dirname $0)/target/porcupi-0.6.9.jar
