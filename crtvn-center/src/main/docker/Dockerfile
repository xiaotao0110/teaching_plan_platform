FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD crtvn-center-1.0-SNAPSHOT.jar /app.jar
RUN apk --update add curl bash ttf-dejavu && \
      rm -rf /var/cache/apk/*
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]