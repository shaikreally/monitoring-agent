FROM eclipse-temurin:17-jre
COPY target/*.jar agent.jar
ENTRYPOINT ["java", "-jar", "/agent.jar"]