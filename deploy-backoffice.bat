@echo off

REM === CONFIGURATION JAVA 17 ===
set JAVA_HOME=C:\Program Files\jdk-17.0.13.11-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

REM === CLEAN + BUILD WAR ===
cd /d C:\Users\Nomena Christian\Documents\Workflow\VISA\Backoffice
call mvn clean package -DskipTests

REM === COPIE DANS TOMCAT ===
copy target\backoffice-0.0.1-SNAPSHOT.war C:\apache-tomcat-10.1.34\webapps\

pause