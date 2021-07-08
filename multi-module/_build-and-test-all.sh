# /bin/bash

set -e

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
fi

if [ -z "$SPRING_DATASOURCE_URL" ] ; then
  export SPRING_DATASOURCE_URL=jdbc:mysql://${DOCKER_HOST_IP}/es-test
  echo Set SPRING_DATASOURCE_URL $SPRING_DATASOURCE_URL
fi

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS $* build -x :e2etest:test

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* :e2etest:cleanTest :e2etest:testClasses

./gradlew ${database}${mode}ComposeBuild
./gradlew ${database}${mode}ComposeUp

./gradlew $BUILD_AND_TEST_ALL_EXTRA_GRADLE_ARGS --offline $* -P ignoreE2EFailures=false :e2etest:cleanTest :e2etest:test

if [ $NO_RM = false ] ; then
  ./gradlew ${database}${mode}ComposeDown
fi
