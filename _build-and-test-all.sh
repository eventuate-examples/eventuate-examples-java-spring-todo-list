# /bin/bash

set -e

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS testClasses

if [ -z "$DOCKER_HOST_IP" ] ; then
  if [ -z "$DOCKER_HOST" ] ; then
    export DOCKER_HOST_IP=`hostname`
  else
    echo using ${DOCKER_HOST?}
    XX=${DOCKER_HOST%\:*}
    export DOCKER_HOST_IP=${XX#tcp\:\/\/}
  fi
  echo set DOCKER_HOST_IP $DOCKER_HOST_IP
fi

if [ "$1" = "--use-existing" ] ; then
  shift;
else
  ./gradlew ${database}${mode}ComposeDown
fi


NO_RM=false

if [ "$1" = "--no-rm" ] ; then
  NO_RM=true
  shift
fi


if [ ! -z "$EXTRA_INFRASTRUCTURE_SERVICES" ]; then
    ./gradlew ${EXTRA_INFRASTRUCTURE_SERVICES}ComposeBuild
    ./gradlew ${EXTRA_INFRASTRUCTURE_SERVICES}ComposeUp
    echo trying again - should do nothing
    ./gradlew -P dockerComposeNoCreate=true ${EXTRA_INFRASTRUCTURE_SERVICES}ComposeUp
fi

if [ -z "$SPRING_DATASOURCE_URL" ] ; then
  export SPRING_DATASOURCE_URL=jdbc:mysql://${DOCKER_HOST_IP}/es-test
  echo Set SPRING_DATASOURCE_URL $SPRING_DATASOURCE_URL
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build -x :e2etest:test

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* :e2etest:cleanTest :e2etest:testClasses

./gradlew ${database}${mode}ComposeBuild
./gradlew -P dockerComposeNoCreate=true ${database}${mode}ComposeUp

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 $EXTRA_PORTS_TO_WAIT_FOR

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* -P ignoreE2EFailures=false :e2etest:cleanTest :e2etest:test

if [ $NO_RM = false ] ; then
  ./gradlew ${database}${mode}ComposeDown
fi
