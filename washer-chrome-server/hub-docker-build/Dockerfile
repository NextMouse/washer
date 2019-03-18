FROM selenium/node-chrome:latest
LABEL authors=networkfox email=ohbugs@foxmail.com
MAINTAINER networkfox

USER root

ENV TZ=Asia/Shanghai WORKDIR_SERVER=/data/server
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && mkdir -p $WORKDIR_SERVER/file/ \
    && curl -SL https://github.com/litaiqing/washer/releases/download/washer-server-jar-1.0.0/washer-chrome-server-0.0.1-SNAPSHOT.jar >> $WORKDIR_SERVER/server.jar \
    && chmod u+x $WORKDIR_SERVER/server.jar

WORKDIR $WORKDIR_SERVER/

EXPOSE 9233

ENTRYPOINT ["java", "-jar", "server.jar"]

