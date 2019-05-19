./gradlew bootJar
cp build/libs/triangulum-0.0.1-SNAPSHOT.jar ~/Desktop/docker/triangulum/ser.jar
cp src/main/resources/application-uat.yml ~/Desktop/docker/triangulum/config/
cp src/main/resources/log4j2.xml ~/Desktop/docker/triangulum/config/
cd ~/Desktop/docker/triangulum
docker stop triangulum-heart
docker run --rm -it -d --name triangulum-heart -p 60001:8906 --network net -v /Users/chengweiou/Desktop/docker/triangulum:/proj/ -w /proj/ openjdk java -jar ser.jar