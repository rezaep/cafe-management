FROM openjdk:11-jre-slim

VOLUME /tmp

EXPOSE 8080

WORKDIR /app

# Add the application's jar to the container
COPY /build/libs/cafe-manager-*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]