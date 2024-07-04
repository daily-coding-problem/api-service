# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim as builder

# The name of the application's jar file
ARG APP_NAME

# Set the working directory to /app
WORKDIR /app

# Copy POM file
COPY pom.xml .

# Install dependencies
RUN mvn -ntp dependency:go-offline

# Copy the source code
COPY src src

# Package the application
RUN mvn -ntp package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim as layers

# Bring in the JAR file from the builder stage
COPY --from=builder target/$APP_NAME.jar .

# Extract the layers
RUN java -Djarmode=layertools -jar $APP_NAME.jar extract

# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim as runtime

# Brining in the extracted layers from the layers stage
COPY --from=layers dependencies/ .
COPY --from=layers snapshot-dependencies/ .
COPY --from=layers spring-boot-loader/ .
COPY --from=layers application/ .

# Run the extracted layers
CMD ["java", "org.springframework.boot.loader.JarLauncher"]
