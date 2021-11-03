FROM openjdk:11
USER root

RUN apt-get update
RUN apt-get install -y openjdk-11-jdk maven

VOLUME ["/root/.m2", "/work"]
