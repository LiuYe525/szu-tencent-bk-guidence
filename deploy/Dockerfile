FROM openjdk:8
# author
MAINTAINER LiuYe

VOLUME /usr/local/src/bk-guidance

RUN mkdir -p /home/bk-guidance

WORKDIR /home/bk-guidance

COPY ./szu-bk-guidance-1.0.jar /home/bk-guidance

ENTRYPOINT ["java","-jar","/home/bk-guidance/szu-bk-guidance-1.0.jar"]