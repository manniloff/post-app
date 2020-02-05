FROM openjdk:8
ADD target/docker-post-app.jar docker-post-app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","docker-post-app.jar"]