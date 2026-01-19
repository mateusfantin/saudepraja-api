#Versão da jdk utilizada no container
FROM eclipse-temurin:21-jre-alpine

#Diretório que será criado
WORKDIR /app

#Copia do arquivo na pasta target para dentro do destino
COPY target/saude-praja-api-0.0.1-SNAPSHOT.jar /app/api.jar

#Porta
EXPOSE 8080

#Executor, comando, arquivo - Trabalhando no WORKDIR
CMD ["java", "-jar", "api.jar"]