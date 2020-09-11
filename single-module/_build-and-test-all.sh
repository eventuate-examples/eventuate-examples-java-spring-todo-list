# /bin/bash

set -e

export COMPOSE_HTTP_TIMEOUT=240

docker="./gradlew ${database}${mode}Compose"

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

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* e2eTest

if [ $NO_RM = false ] ; then
  ${docker}Down
fi
