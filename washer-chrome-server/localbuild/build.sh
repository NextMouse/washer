#!/bin/bash
# build Dockerfile shell
# author networkfox

echo "build starting..."
FILE_NAME=server.jar
artifactId=`awk '/<artifactId>[^<]+<\/artifactId>/{gsub(/<artifactId>|<\/artifactId>/,"",$1);print $1;exit;}' ../pom.xml`
version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' ../pom.xml`
echo " ==> $artifactId-$version"
[ ! -e "$FILE_NAME" ] && {
    echo " ==> mvn package..."
    mv clean package
    cp ../target/$artifactId-$version.jar ./FILE_NAME.jar
}
echo " ==> build Dockerfile..."
docker build -t networkfox/chrome-java-server:$version .
#rm -f server.jar
echo "build end!"