FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests && \
    echo "Build completed. Contents of target:" && \
    ls -la target/ && \
    echo "JAR files found:" && \
    ls -la target/*.jar

EXPOSE 8080

CMD ["java", "-jar", "target/recipe-app-backend-0.0.1-SNAPSHOT.jar"]