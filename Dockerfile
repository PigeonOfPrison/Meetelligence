FROM eclipse-temurin:21-jdk
LABEL authors="Mohammad Hammad (A.K.A PigeonOfPrison)"

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]