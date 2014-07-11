#!/bin/bash
cd $(dirname $0)

if [ ! -d "$ANDROID_HOME" ]; then
    echo "ANDROID_HOME is not available"
    exit
fi

cd ../complete
./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
    exit $ret
fi
rm -rf build

cd ../initial
../complete/gradlew wrapper -b ../initial/build.gradle
./gradlew build
ret=$?
if [ $ret -ne 0 ]; then
    exit $ret
fi
rm -rf build
rm -rf gradle
rm gradlew*

exit
