FROM avail/jce-java
MAINTAINER Avail<694208570@qq.com>

VOLUME /tmp
COPY ./target/product-ms-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT [ "java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "/app.jar" ]

EXPOSE 8040 10912