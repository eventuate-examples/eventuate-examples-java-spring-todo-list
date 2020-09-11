#! /bin/bash

export database=postgres
export mode=polling
export SPRING_PROFILES_ACTIVE=postgres

./_build-and-test-all.sh $* -P eventuateDriver=local
