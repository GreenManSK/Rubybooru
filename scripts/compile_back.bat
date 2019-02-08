@echo off
cd /D %~dp0\..\
cd back
mvn clean install & set /p DUMMY=Hit ENTER to continue...