FROM selenium/node-chrome:latest
LABEL authors=networkfox email=ohbugs@foxmail.com
MAINTAINER networkfox

USER root

ENV TZ=Asia/Shanghai WORKDIR_SERVER=/data/server
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && mkdir -p $WORKDIR_SERVER/file/ \
    && cd $WORKDIR_SERVER/ \
    && curl -SL https://github.com/litaiqing/washer/releases/download/washer-server-jar-1.0.0/server.jar >> server.jar \
    && ls -l $WORKDIR_SERVER/ \ 
    && chmod u+x server.jar

EXPOSE 9233

ENTRYPOINT ["java", "-jar", "server.jar"]

