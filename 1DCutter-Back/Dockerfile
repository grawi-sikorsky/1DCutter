FROM maven:latest as build
# Copy only pom.xml of your projects and download dependencies
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline
# Copy all other project files and build project
COPY . .
RUN mvn -B install -DskipTests

# Run stage
FROM openjdk:17-alpine
COPY --from=build ./target/*.jar ./
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/onedcutter-0.1.0-SNAPSHOT.jar"]

# COPY /home/debian/nginx-proxy/certs/api.cutter.grawires.pl/* /src/main/resources/

# RUN \
#         openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out cert.pkcs12

# EXPOSE 8443