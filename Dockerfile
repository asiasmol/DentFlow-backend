FROM openjdk:19-jdk-alpine
ADD target/jwt-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar jwt-0.0.1-SNAPSHOT.jar