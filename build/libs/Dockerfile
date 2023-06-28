FROM openjdk:17
ARG JAR_FILE=LET-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.config.location=/secret/application.yml","-jar","/app.jar"]