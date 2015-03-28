#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-8-oracle/
if [[ "$1" != "shell" ]]; then mvn clean package; fi
java -jar $(dirname $0)/target/porcupi-0.6.9.jar $1
