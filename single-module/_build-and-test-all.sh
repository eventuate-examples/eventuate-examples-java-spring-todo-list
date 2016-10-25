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

./gradlew $* build

if [ -z "$EVENTUATE_LOCAL" ] && [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

./gradlew --offline $* cleanTest

${DOCKER_COMPOSE?} up -d standaloneservice

./wait-for-services.sh $DOCKER_HOST_IP 8080

./gradlew --offline $* e2eTest

if [ $NO_RM = false ] ; then
  ${DOCKER_COMPOSE?} stop
  ${DOCKER_COMPOSE?} rm -v --force
fi