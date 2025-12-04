# Заменяем старый образ openjdk на поддерживаемый
FROM eclipse-temurin:8-jdk

WORKDIR /app

COPY target/post-app.war app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]
