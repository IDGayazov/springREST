FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM tomcat:9.0.89-jdk11

ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH

COPY --from=build /app/target/landmarks.war $CATALINA_HOME/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]