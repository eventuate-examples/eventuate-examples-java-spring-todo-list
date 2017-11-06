# /bin/bash

set -e

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

DOCKER_COMPOSE="docker-compose -p java-spring-todo-list"

while [ "$1" = "-f" ] ; do
  shift;
  DOCKER_COMPOSE="$DOCKER_COMPOSE -f ${1?}"
  shift
done

if [ "$1" = "--use-existing" ] ; then
  shift;
else
  ${DOCKER_COMPOSE?} stop
  ${DOCKER_COMPOSE?} rm -v --force
fi


NO_RM=false

if [ "$1" = "--no-rm" ] ; then
  NO_RM=true
  shift
fi

${DOCKER_COMPOSE?} up -d mysql $EXTRA_INFRASTRUCTURE_SERVICES

if [ -z "$SPRING_DATASOURCE_URL" ] ; then
  export SPRING_DATASOURCE_URL=jdbc:mysql://${DOCKER_HOST_IP}/es-test
  echo Set SPRING_DATASOURCE_URL $SPRING_DATASOURCE_URL
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build -x :e2etest:test

if [ -z "$EVENTUATE_LOCAL" ] && [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* :e2etest:cleanTest :e2etest:testClasses

${DOCKER_COMPOSE?} build

${DOCKER_COMPOSE?} up -d commandsideservice querysideservice

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082 $EXTRA_PORTS_TO_WAIT_FOR

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* -P ignoreE2EFailures=false :e2etest:cleanTest :e2etest:test

if [ $NO_RM = false ] ; then
  ${DOCKER_COMPOSE?} stop
  ${DOCKER_COMPOSE?} rm -v --force
fi
