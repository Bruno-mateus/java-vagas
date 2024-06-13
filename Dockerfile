# Use a imagem mais recente do Ubuntu como a imagem base para o estágio de build
FROM ubuntu:latest AS build

# Atualiza a lista de pacotes disponíveis no sistema
RUN apt-get update

# Instala o OpenJDK 17, necessário para compilar e executar aplicativos Java
RUN apt-get install openjdk-17-jdk -y

# Instala o Maven, uma ferramenta de automação de build para projetos Java
RUN apt-get install maven -y

# Copia todo o conteúdo do diretório atual para o diretório de trabalho da imagem
COPY . .

# Executa o comando Maven para limpar qualquer build anterior e construir o projeto
RUN mvn clean install

# Usa a imagem mais leve do OpenJDK 17 para a execução final da aplicação
FROM openjdk:17-jdk-slim

# Expõe a porta 8080 para permitir que o tráfego externo chegue à aplicação
EXPOSE 8080

# Copia o JAR gerado no estágio de build para o diretório de trabalho da imagem final
COPY --from=build /target/gestão_vagas-0.0.1.jar app.jar

# Define o comando de entrada para iniciar a aplicação Java quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]
