FROM avail/jce-java
MAINTAINER Avail<694208570@qq.com>

VOLUME /tmp
COPY ./target/config-ms-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT [ "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app.jar" ]

EXPOSE 10888 10481