#!/usr/bin/env bash
./gradlew -Pprod bootRepackage
cp src/main/docker/Dockerfile build/libs
cd build/libs
docker build -t haklist .
#https://linuxconfig.org/remove-all-containners-based-on-docker-image-name
docker ps -a | awk '{ print $1,$2 }' | grep tyrelltle/hacker | awk '{print $1 }' | xargs -I {} docker rm {}
docker images | awk '{ print $1,$2 }' | grep tyrelltle/hacker | awk '{print $1 }' | xargs -I {} docker rmi {}
docker images | awk '{ print $1,$2,$3 }' | grep '^haklist .*' | awk '{print $3 }' | xargs -I {} docker tag {} tyrelltle/hacker
docker push tyrelltle/hacker
