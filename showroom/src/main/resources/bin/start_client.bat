@echo off

rem Calculate SHOWROOM_HOME
set SHOWROOM_HOME=%cd%\..
set BIN_DIR=%SHOWROOM_HOME%\bin
set LIB_DIR=%SHOWROOM_HOME%\lib
set JAVA_HOME=%BIN_DIR%\jdk-${jdk.version}
set JAVA_CMD=%JAVA_HOME%\bin\java

rem JVM arguments...
set JVM_ARGS=-Dshowroom.home=%SHOWROOM_HOME%

rem Debug arguments 
rem set JVM_ARGS="%JVM_ARGS% -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y" 

rem Running ShowRoom...
set CMD=%JAVA_CMD% %JVM_ARGS% -classpath %LIB_DIR%\* --module-path %LIB_DIR% --add-modules=com.puresoltechnologies.javafx.showroom com.puresoltechnologies.javafx.showroom.ShowRoom
%CMD%
