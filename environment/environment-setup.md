docker-compose 部署mongo&rabbitmq

```
docker pull rabbitmq:3.12-rc-management

docker run -d --name=rabbitmq rabbitmq:3.12-rc-management

mkdir -p ./rabbit/data
mkdir -p ./postgres/data
mkdir -p ./redis/data
mkdir -p ./redis/conf

上传redis.conf到redis/conf的文件夹下

cd rabbit

docker cp rabbitmq:/etc/rabbitmq ./
mv rabbitmq conf
docker rm -f rabbitmq

cd ..

docker-compose up -d
```

mq至此部署成功，下面是mongo的配置过程

```
docker exec -it mongo bash

mongosh "mongodb://localhost:27017" -u mongo -p mongo_password

use bkguidance

db.createUser( { user: "mongodb", pwd: "mongo_password", roles: [ { role: "root", db: "admin" } ] } )


```

如果想直接使用项目配置文件启动则还需要在主机上设置host将对应中间件的ip映射到host上
