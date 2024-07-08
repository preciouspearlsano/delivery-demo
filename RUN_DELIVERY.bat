@echo off
setlocal

echo Step 1: Install JAR to local Maven repository
call mvn install:install-file -Dfile=C:\Users\PreciousS\Documents\workspace-palawan-card-bill\STS17-dev\delivery-demo\ext\deliver-common-config-0.0.1-SNAPSHOT.jar -DgroupId=com.delivery.common.config -DartifactId=deliver-common-config -Dversion=0.0.1-SNAPSHOT -Dpackaging=jar

echo Step 2: Set JAVA_HOME and PATH
set PATH=C:\Program Files\Eclipse Adoptium\jdk-17.0.10.7-hotspot\bin;%PATH%
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.10.7-hotspot

echo Step 3: Execute Spring Boot application
call mvn clean package
call mvn dependency:tree
call java -jar target\delivery-demo.jar
