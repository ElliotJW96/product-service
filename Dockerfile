FROM eclipse-temurin:17-jre-alpine
COPY build/libs/product-*-all.jar product-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "product-service.jar"]
