#
# Build stage
##
FROM maven:3.9.11 AS build
COPY . /app
WORKDIR /app
RUN mvn -Dmaven.test.skip=true -f /app/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:17-jdk
#RUN apk add --no-cache msttcorefonts-installer fontconfig
#RUN update-ms-fonts
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]