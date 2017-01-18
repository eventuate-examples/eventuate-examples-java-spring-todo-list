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

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build

if [ -z "$EVENTUATE_LOCAL" ] && [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* cleanTest

${DOCKER_COMPOSE?} build

${DOCKER_COMPOSE?} up -d standaloneservice

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* e2eTest

if [ $NO_RM = false ] ; then
  ${DOCKER_COMPOSE?} stop
  ${DOCKER_COMPOSE?} rm -v --force
fi
