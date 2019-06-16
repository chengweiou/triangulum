./gradlew bootJar
cp build/libs/triangulum-0.0.1-SNAPSHOT.jar ~/Desktop/docker/universe/triangulum/ser.jar
cp src/main/resources/application-uat.yml ~/Desktop/docker/universe/triangulum/config/
cp src/main/resources/log4j2.xml ~/Desktop/docker/universe/triangulum/config/
cd ~/Desktop/docker/universe/triangulum
docker stop triangulum
docker run --rm -it -d --name triangulum -p 60003:8906 --network net -v /Users/chengweiou/Desktop/docker/universe/triangulum:/proj/ -w /proj/ openjdk java -jar ser.jar