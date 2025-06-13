# Step 1: Use Maven to build the Spring Boot app
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy everything and build the project
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Use a minimal runtime image to run the app
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
