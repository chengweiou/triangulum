./gradlew bootJar
cp build/libs/triangulum-0.0.1-SNAPSHOT.jar ~/Desktop/docker/universe/triangulum/ser.jar
#cp src/main/resources/application.yml ~/Desktop/docker/universe/triangulum/config/
cp src/main/resources/application-uat.yml ~/Desktop/docker/universe/triangulum/config/
cp src/main/resources/log4j2.yml ~/Desktop/docker/universe/triangulum/config/
cp docker-compose.yml ~/Desktop/docker/universe/triangulum/docker-compose.yml
cd ~/Desktop/docker/universe/triangulum

docker compose down
docker compose rm -f
docker compose up -d
