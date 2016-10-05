# /bin/bash

set -e

DOCKER_COMPOSE="docker-compose -p java-spring-todo-list"

if [ "$1" = "-f" ] ; then
  shift;
  DOCKER_COMPOSE="$DOCKER_COMPOSE -f ${1?}"
  shift
fi

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

if [ -z "$DOCKER_HOST_IP" ] ; then
  if which docker-machine >/dev/null; then
    export DOCKER_HOST_IP=$(docker-machine ip default)
  else
    export DOCKER_HOST_IP=localhost
 fi
 echo set DOCKER_HOST_IP $DOCKER_HOST_IP
fi

if [ -z "$SPRING_DATASOURCE_URL" ] ; then
  export SPRING_DATASOURCE_URL=jdbc:mysql://${DOCKER_HOST_IP}/es-test
  echo Set SPRING_DATASOURCE_URL $SPRING_DATASOURCE_URL
fi

./gradlew $* build -x :e2etest:test

if [ -z "$EVENTUATE_LOCAL"] && [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

./gradlew --offline $* :e2etest:cleanTest :e2etest:testClasses

${DOCKER_COMPOSE?} up -d commandsideservice querysideservice

./wait-for-services.sh $DOCKER_HOST_IP 8081 8082

./gradlew --offline $* :e2etest:cleanTest :e2etest:test

if [ $NO_RM = false ] ; then
  ${DOCKER_COMPOSE?} stop
  ${DOCKER_COMPOSE?} rm -v --force
fi