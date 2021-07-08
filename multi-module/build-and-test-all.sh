#! /bin/bash

export COMPOSE_HTTP_TIMEOUT=240

./_build-and-test-all.sh $*
