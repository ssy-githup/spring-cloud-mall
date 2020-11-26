#!/bin/bash

cd `dirname $0`
cd ..
DEPLOY_DIR=`pwd`

SERVER_NAME=gateway
JAR_File=$DEPLOY_DIR/lib/cloud_gateway.jar

echo " "

PIDS=`ps -ef | grep java | grep "$JAR_File" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "The server $SERVER_NAME is running!"
    echo "PID: $PIDS"
    echo " "
    exit 1
fi

if [ -z "$PIDS" ]; then
    echo "The server $SERVER_NAME is stop!"
    echo " "
    exit 1
fi