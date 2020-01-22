#!/bin/bash

cd `dirname $0`
cd ..
DEPLOY_DIR=`pwd`

SERVER_NAME=gateway
JAR_File=$DEPLOY_DIR/lib/cloud_gateway.jar

echo " "

PIDS=`ps -ef | grep java | grep "$JAR_File" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The server $SERVER_NAME does not started!"
    echo " "
    exit 1
fi

echo -e "Stopping the server $SERVER_NAME ...\c"
for PID in $PIDS ; do
    kill $PID > /dev/null 2>&1
done

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1

    PIDS=`ps -ef | grep java | grep "$JAR_File" |awk '{print $2}'`
    if [ -z "$PIDS" ]; then
        COUNT=2
    fi
done

echo "OK!"
echo " "