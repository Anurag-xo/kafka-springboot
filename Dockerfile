# Use a multi-stage build to create a lean final image.

# 1. Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the 'core' project first
COPY ../core ./core
# Install the 'core' project to the local Maven repository
RUN cd core && mvn clean install

# Copy the 'ProductsMicroservice' project
COPY . .
# Build the 'ProductsMicroservice' project
RUN mvn clean install -DskipTests

# 2. Package stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# The application will run on a random port by default as configured in application.properties (server.port=0).
# To run on a specific port, you can override the server.port property when running the container, e.g.:
# docker run -p 8080:8080 -e "SPRING_APPLICATION_JSON={\"server.port\":8080}" <image-name>
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]