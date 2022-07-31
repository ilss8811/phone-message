FROM openjdk:11

WORKDIR /usr/phone-message

COPY ./ /usr/phone-message

RUN ./mvnw clean

RUN ./mvnw install

RUN ./mvnw compile

RUN ./mvnw package