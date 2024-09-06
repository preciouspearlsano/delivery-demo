@echo off
setlocal



echo Step 1: Set JAVA_HOME and PATH
set PATH=C:\Program Files\Eclipse Adoptium\jdk-17.0.10.7-hotspot\bin;%PATH%
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.10.7-hotspot

echo Step 2: Execute Spring Boot application
REM call mvn clean package
REM call mvn dependency:tree
call java -jar -Dspring.config.location=classpath:/note-demo-dev.yaml target\note-demo.jar
