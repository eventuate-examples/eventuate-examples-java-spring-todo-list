# /bin/bash


if [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

set -e
docker-compose stop
docker-compose rm -v --force

docker-compose up -d mysql

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

./gradlew $* build

docker-compose up -d standaloneservice

./gradlew --offline $* cleanTest

echo -n waiting for service....

set +e

while [[ true ]]; do

    curl -q http://${DOCKER_HOST_IP?}:8080/health >& /dev/null
    if [[ "$?" -eq "0" ]]; then
        echo connected
        break
    fi

    echo -n .
    sleep 1
done

set -e

./gradlew --offline $* e2eTest

docker-compose stop
docker-compose rm -v --force

