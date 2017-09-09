FROM registry.wang-guanjia.com:5000/java:8
MAINTAINER happylifeplat
VOLUME /tmp
EXPOSE 80
ENV TZ=Asia/Shanghai

#定义环境变量，脚本中需要获取
ENV env=dev
ENV APP_NAME=wechat-service
ENV JAR_PATH=/wechat-service.jar
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENV OTHER_JAVA_OPTS=
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN curl http://git.wang-guanjia.com/mobile/apm-script/raw/master/docker-apm.sh > docker-apm.sh && bash docker-apm.sh
ADD impl/target/wechat-service.jar $JAR_PATH
RUN bash -c 'touch /app.jar'
ADD entrypoint.sh /entrypoint.sh
CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /wechat-service.jar