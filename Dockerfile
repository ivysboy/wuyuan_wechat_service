FROM java:8-jre
VOLUME /tmp
EXPOSE 9991

ENV APP_NAME=wechat-service
ENV JAR_PATH=/wechat-service.jar
ENV APP_ENV=DEV

ADD impl/target/wechat-service.jar $JAR_PATH
RUN sh -c 'touch /wechat-service.jar'
ENV JAVA_OPTS="-Djava.net.preferIPv4Stack=true"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Denv=$APP_ENV -Djava.security.egd=file:/dev/./urandom -jar /wechat-service.jar" ]