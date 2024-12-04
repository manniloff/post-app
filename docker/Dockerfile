FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/post-app.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java", "-jar", "/app.war"]
