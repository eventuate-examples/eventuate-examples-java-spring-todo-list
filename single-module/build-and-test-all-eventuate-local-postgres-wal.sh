#! /bin/bash

. ./set-env-postgres.sh

export EXTRA_INFRASTRUCTURE_SERVICES=postgreswalcdc
export EVENTUATE_LOCAL=yes
export database=postgres
export mode=wal

./_build-and-test-all.sh $* -P eventuateDriver=local
