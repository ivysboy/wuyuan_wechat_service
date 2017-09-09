#!/bin/sh
set -e

properties="aliapm.properties"
name=$APP_NAME

if [  -e $properties ]; then
    if [ -n $env ]; then
	    name="$name-$env";
    fi

    sed -i "/appName=/c appName=$name" $properties
fi

java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar $JAR_PATH

exec "$@"