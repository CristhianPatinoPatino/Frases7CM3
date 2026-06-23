FROM eclipse-temurin:21-jdk-alpine
LABEL authors="crist"
ARG JAR_FILE=target/Ejercicio06-Frases-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_frase.jar
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app_frase.jar"]