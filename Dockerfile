# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the JAR file from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Build-time arguments (can be overridden during docker build)
ARG APP_NAME=demo
ARG DB_HOST=localhost
ARG DB_PORT=5432
ARG DB_NAME=postgres
ARG DB_USERNAME=postgres
ARG DB_PASSWORD=mounika
ARG DDL_AUTO=update
ARG SHOW_SQL=true
ARG FORMAT_SQL=true
ARG SPRING_PROFILES_ACTIVE=default

# Runtime environment variables (can be overridden during docker run)
ENV APP_NAME=${APP_NAME}
ENV DB_HOST=${DB_HOST}
ENV DB_PORT=${DB_PORT}
ENV DB_NAME=${DB_NAME}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DDL_AUTO=${DDL_AUTO}
ENV SHOW_SQL=${SHOW_SQL}
ENV FORMAT_SQL=${FORMAT_SQL}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

