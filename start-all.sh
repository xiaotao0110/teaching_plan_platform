#!/bin/sh

# 用来启动所有的项目
echo "------------------开始构建镜像------------------"

echo "------------------构建register镜像"
cd  $WORKSPACE/register-center
mvn clean install  package docker:build -Dmaven.test.skip=true

echo "------------------构建api-gateway镜像"
cd   $WORKSPACE/api-gateway
mvn clean install  package docker:build -Dmaven.test.skip=true

echo "------------------构建oauth镜像"
cd   $WORKSPACE/oauth-center
mvn clean install  package docker:build -Dmaven.test.skip=true

echo "------------------构建crtvn镜像"
cd   $WORKSPACE/crtvn-center
mvn clean install  package docker:build -Dmaven.test.skip=true

echo "------------------构建镜像完成------------------"

echo "-----------------------------------------------"

echo "------------------开始启动容器------------------"


echo "------------------启动register容器"
docker run -id  --name=register-center  -p 1000:1000  -d  teaching/register-center:latest /bin/bash

echo "------------------启动api-gateway容器"
docker run -id  --name=api-gateway  -p 1005:1005  -d  teaching/api-gateway:latest /bin/bash

echo "------------------启动oauth容器"
docker run -id  --name=oauth-center  -p 1001:1001  -d  teaching/oauth-center:latest /bin/bash

echo "------------------启动crtvn容器"
docker run -id  --name=crtvn-center  -p 1003:1003  -d  teaching/crtvn-center:latest /bin/bash

echo "------------------启动容器完成------------------"