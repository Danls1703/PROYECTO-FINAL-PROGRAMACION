@echo off

REM Establece el Classpath para la ejecución: incluye bin y todos los JARs
set CLASSPATH=lib/*;bin

echo Iniciando Modo CONSOLA (Menu)
echo =====================================

java -cp %CLASSPATH% com.proyecto.Main

pause