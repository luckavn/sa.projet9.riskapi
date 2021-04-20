FROM openjdk:11-jdk-alpine
LABEL responsable="lucasvannier@gmail.com"
EXPOSE 8083:8083
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} riskapi.jar
ENTRYPOINT ["java","-jar","/riskapi.jar"]