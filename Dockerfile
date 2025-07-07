# Imagen base liviana con Java 21
FROM eclipse-temurin:17-jdk

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
