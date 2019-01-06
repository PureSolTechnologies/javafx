#!/bin/sh

# Calculate SHOWROOM_HOME
SHOWROOM_HOME="$( cd "$( dirname $0 )/.." && pwd )"
BIN_DIR=$SHOWROOM_HOME/bin
LIB_DIR=$SHOWROOM_HOME/lib
JAVA_HOME=$BIN_DIR/jdk-${jdk.version}
JAVA_CMD=$JAVA_HOME/bin/java

# JVM arguments...
JVM_ARGS="-Dshowroom.home=$SHOWROOM_HOME"

# Debug arguments 
#JVM_ARGS="$JVM_ARGS -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y" 

# Running ShowRoom client...
CMD=$JAVA_CMD $JVM_ARGS -classpath $LIB_DIR/* --module-path %LIB_DIR% --add-modules=com.puresoltechnologies.javafx.showroom com.puresoltechnologies.javafx.showroom.ShowRoom
$CMD
