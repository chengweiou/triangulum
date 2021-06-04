### uat 环境
network
docker network create net

#### 上传
```
./uat.sh
```
first time:
```
change active profile to uat
mkdir -pv ~/Desktop/docker/triangulum/config
cp src/main/resources/application.yml ~/Desktop/docker/triangulum/config/
chmod +x uat.sh
./uat.sh
```


##### 单独启动容器
```
docker network net
docker run --rm -it -d --name triangulum -p 60003:8906 --network net -v ~/Desktop/docker/universe/triangulum:/proj/ -w /proj/ openjdk java -jar ser.jar
```