@ECHO OFF
set file=
if EXIST %1 set file=%1


%JAVA_HOME%\bin\java -classpath .\build\libs\* io.dukecvar.monpoke.Play %file%
