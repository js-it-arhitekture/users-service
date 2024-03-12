# Uporabljamo Java 11 kot osnovno sliko
FROM adoptopenjdk/openjdk11:alpine-jre

# Nastavimo delovni imenik v notranjosti Docker kontejnerja
WORKDIR /app


# The application's jar file
COPY /out/artifacts/nakup_vstopnic_main_jar/nakup_vstopnic.main.jar app.jar

# Nastavimo splošne argumente za Spring Boot aplikacijo
# ARG SPRING_PROFILES_ACTIVE=production
# ARG SERVER_PORT=8080

# Definiramo, kateri port bo naša aplikacija uporabljala
EXPOSE 8080

# Zagon Spring Boot aplikacije
CMD ["java", "-jar", "app.jar"]