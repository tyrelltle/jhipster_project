FROM ubuntu
VOLUME /tmp
RUN apt-get update

RUN apt-get install -y software-properties-common

RUN add-apt-repository -y ppa:webupd8team/java

RUN apt-get update

RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections

RUN apt-get install -y oracle-java8-installer



ADD haklist-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["./app.war","--spring.profiles.active=prod"]
EXPOSE 8080

