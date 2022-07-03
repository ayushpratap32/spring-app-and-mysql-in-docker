FROM openjdk:11
ADD target/order-service-application.jar order-service-application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","order-service-application.jar"]