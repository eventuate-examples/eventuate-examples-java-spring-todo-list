#! /bin/bash

export EXTRA_INFRASTRUCTURE_SERVICES=cdcservice
export EVENTUATE_LOCAL=yes
export EXTRA_PORTS_TO_WAIT_FOR=8099

. set-env.sh
./_build-and-test-all.sh -f docker-compose-eventuate-local.yml $BUILD_AND_TEST_ALL_EVENTUATE_LOCAL_EXTRA_COMPOSE_ARGS $* -P eventuateDriver=local
