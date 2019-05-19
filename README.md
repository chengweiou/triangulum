### uat 环境
network
docker network create net

mysql 8
docker run --rm -it --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql/mysql-server

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