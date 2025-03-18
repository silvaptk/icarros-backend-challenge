FROM maven:3.9.9-eclipse-temurin-21-jammy

RUN apt-get update && apt-get install -y inotify-tools

WORKDIR /app

COPY pom.xml /app
COPY src /app/src

CMD mvn -Dgroups=unit test && while inotifywait -r -e modify /app; do mvn -Dgroups=unit test; done