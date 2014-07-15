#!/bin/sh
cd $(dirname $0)

cd ../complete
./gradlew clean build
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

./gradlew clean
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

mvn clean package
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

mvn clean
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

cd ../initial
./gradlew clean projects
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

mvn clean
ret=$?
if [ $ret -ne 0 ]; then
  exit $ret
fi

exit
