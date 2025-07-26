FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

RUN apk update && apk add --no-cache maven

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/FitForge-0.0.1-SNAPSHOT.jar"]
aws ecr describe-repositories --repository-names fitforge --region ap-southeast-2