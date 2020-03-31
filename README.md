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