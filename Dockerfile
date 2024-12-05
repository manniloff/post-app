FROM openjdk:8
ADD target/post-app.jar post-app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","docker-post-app.jar"]