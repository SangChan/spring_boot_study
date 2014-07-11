#!/bin/bash
cd $(dirname $0)
if [[ "$OSTYPE" == "linux-gnu" ]]; then
    wget http://dl.google.com/android/android-sdk_r22.6.2-linux.tgz -nv
    tar xvf android-sdk_r22.6.2-linux.tgz > /dev/null
    export ANDROID_HOME=`pwd`/android-sdk-linux 
    export PATH=${ANDROID_HOME}/tools/:${ANDROID_HOME}/platform-tools/:${PATH}
    echo "y" | android update sdk -f -u -a -t tools,platform-tools,build-tools-19.0.3,android-19,extra-android-m2repository,extra-android-support
#elif [[ "$OSTYPE" == "darwin"* ]]; then
else
    echo "OS is not linux"
    exit
fi
./run.sh
