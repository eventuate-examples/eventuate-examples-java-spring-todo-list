# /bin/bash

set -e
docker-compose stop

docker-compose up -d mysql

./gradlew clean build

docker-compose up -d standaloneservice

echo -n waiting for service....

set +e

while [[ true ]]; do
        nc -z -w 4 ${DOCKER_HOST_IP?} 8080
        if [[ "$?" -eq "0" ]]; then
                echo connected
                break
        fi
        echo -n .
        sleep 1
done

./gradlew :e2etest:cleanTest :e2etest:test


