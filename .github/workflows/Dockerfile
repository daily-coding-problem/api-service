# Use the official Maven image to build the project
FROM maven:3.8.5-openjdk-17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml .

# Cache dependencies by running dependency:go-offline
RUN mvn -ntp dependency:go-offline

# Copy the project files to the working directory
COPY . .

# Build the project using Maven
RUN mvn clean install -DskipTests

# Run tests
CMD ["mvn", "test", "-Dspring.profiles.active=test"]
