FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY mvnw pom.xml ./
COPY .mvn .mvn

COPY src ./src

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]