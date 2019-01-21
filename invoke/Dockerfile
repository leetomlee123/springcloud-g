FROM mcr.microsoft.com/java/jdk:8u192-zulu-alpine
VOLUME /tmp
ADD target/invoke-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
