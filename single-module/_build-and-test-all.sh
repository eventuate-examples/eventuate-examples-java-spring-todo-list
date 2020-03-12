# /bin/bash

set -e

docker="./gradlew ${database}${mode}Compose"

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
  ${docker}Down
fi


NO_RM=false

if [ "$1" = "--no-rm" ] ; then
  NO_RM=true
  shift
fi

if [ ! -z "$EXTRA_INFRASTRUCTURE_SERVICES" ]; then
    ./gradlew ${EXTRA_INFRASTRUCTURE_SERVICES}ComposeBuild
    ./gradlew ${EXTRA_INFRASTRUCTURE_SERVICES}ComposeUp
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* cleanTest

${docker}Build
${docker}Up

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* e2eTest

if [ $NO_RM = false ] ; then
  ${docker}Down
fi
