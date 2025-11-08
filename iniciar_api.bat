@echo off
REM ==========================================================
REM Script de Ejecución del Proyecto - MODO CONSOLA (MENU)
REM ==========================================================

REM Define el separador de classpath para Windows (punto y coma)
set CP_SEP=;

REM Define el CLASSPATH con todas las dependencias usando el separador correcto:
set CLASSPATH=bin%CP_SEP%lib\jackson-annotations-2.19.2.jar%CP_SEP%lib\jackson-core-2.19.2.jar%CP_SEP%lib\jackson-databind-2.19.2.jar%CP_SEP%lib\mysql-connector-j-9.4.0.jar%CP_SEP%lib\spark-core-2.9.4.jar%CP_SEP%lib\slf4j-api-1.7.36.jar%CP_SEP%lib\slf4j-simple-1.7.36.jar%CP_SEP%lib\proyecto.jar%CP_SEP%lib\javax.servlet-api-3.1.0.jar%CP_SEP%lib\jetty-server-9.4.48.v20220622.jar%CP_SEP%lib\jetty-util-9.4.48.v20220622.jar%CP_SEP%lib\jetty-http-9.4.48.v20220622.jar%CP_SEP%lib\jetty-io-9.4.48.v20220622.jar

echo ==========================================================
echo CLASSPATH usado: %CLASSPATH%
echo ==========================================================
echo.

echo 💻 Ejecutando Modo CONSOLA (Menu Principal)
echo ==========================================================

REM Ejecuta la clase Main sin argumentos, lo que abre el MENU (Consola)
java -cp "%CLASSPATH%" Main

pause