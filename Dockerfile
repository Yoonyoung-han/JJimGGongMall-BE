#Dockerfile
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY ./build/libs/*.jar ./

ENTRYPOINT ["java","-jar","/app/JJimGGongMall-0.0.1-SNAPSHOT.jar"]