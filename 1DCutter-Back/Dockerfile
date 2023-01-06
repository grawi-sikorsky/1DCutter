FROM maven:latest as build
# Copy only pom.xml of your projects and download dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline
# Copy all other project files and build project
COPY . .
RUN mvn -B install -DskipTests

# Run stage
FROM openjdk:17
COPY --from=build ./target/*.jar ./
# COPY --from=build ./target/onedcutter-0.1.0-SNAPSHOT.jar /app/onedcutter-0.1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/onedcutter-0.1.0-SNAPSHOT.jar"]
