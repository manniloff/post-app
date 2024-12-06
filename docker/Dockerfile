FROM openjdk:17-jdk-slim
ARG JAR_FILE=post-app.war
COPY ${JAR_FILE} post-app.war
ENTRYPOINT ["java", "-jar", "/app.war"]
