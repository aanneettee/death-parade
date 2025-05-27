FROM maven:3.9.2-eclipse-temurin-17-alpine AS builder

WORKDIR /app
COPY pom.xml .
# Сначала кэшируем зависимости
RUN mvn dependency:go-offline -B
COPY src ./src
# Затем собираем проект
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/death_parade-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
# Исправляем имя файла в ENTRYPOINT
ENTRYPOINT ["java", "-jar", "app.jar"]