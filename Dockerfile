FROM openjdk:11

MAINTAINER Andrii Holinko

RUN mkdir -p /app

WORKDIR /app

COPY . .

COPY wait-for-it.sh wait-for-it.sh
#COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod +x wait-for-it.sh

#ENTRYPOINT [ "/bin/bash", "-c" ]
