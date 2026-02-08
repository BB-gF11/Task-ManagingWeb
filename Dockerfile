FROM eclipse-temurin:17

LABEL mentainer="Bushra"

WORKDIR /app

COPY target/TaskManageWebb-0.0.1-SNAPSHOT.jar TaskManageWebb.jar

ENTRYPOINT ["java","-jar","TaskManageWebb.jar"]

