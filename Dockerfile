# Start with a base image containing Java runtime (JDK 17)
FROM openjdk:17-slim as build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven

# Set the current working directory inside the image
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN mvn clean package -DskipTests

RUN mv target/custom-diff-*.jar target/custom-diff.jar

ENTRYPOINT ["java","-jar","target/custom-diff.jar"]