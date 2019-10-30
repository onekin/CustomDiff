#### Stage 1: Build the application
FROM maven:3.6.2-jdk-8  as build

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
RUN mvn package -DskipTests

RUN mv target/custom-diff-*.jar target/custom-diff.jar

ENTRYPOINT ["java","-jar","target/custom-diff.jar"]