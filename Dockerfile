# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the fat JAR
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/target/wordsnake-hunt-1.0.0-jar-with-dependencies.jar app.jar

# Copy resources (XML files and DTD)
COPY res ./res

# Default command to run the application
# Usage: docker run <image> process=l input=res/sj_p1_probleminstanz.xml output=res/sj_p1_loesung_out.xml
ENTRYPOINT ["java", "-jar", "app.jar"]
