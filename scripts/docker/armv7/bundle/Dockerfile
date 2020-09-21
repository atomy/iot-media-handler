FROM arm32v7/openjdk:11-jre

RUN apt-get update && \
    apt-get install -y procps && \
    apt-get clean -y && rm -rf /var/lib/apt/lists/*

ADD build/libs/app-all-1.0.0.jar /app/app.jar
ADD current_version /app/current_version

WORKDIR /app

CMD ["java", "-jar", "/app/app.jar"]