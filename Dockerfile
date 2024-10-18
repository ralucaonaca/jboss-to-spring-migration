# Start with a base image containing Java runtime
FROM openjdk:21-jdk

# Add the application's jar to the image
COPY target/kitchensink-0.0.1-SNAPSHOT.jar kitchensink-0.0.1-SNAPSHOT.jar
# Command to execute the application
ENTRYPOINT ["java", "-jar", "kitchensink-0.0.1-SNAPSHOT.jar"]