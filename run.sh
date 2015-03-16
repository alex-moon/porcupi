#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-8-oracle/
mvn clean package; java -jar $(dirname $0)/target/porcupi-0.6.9.jar
