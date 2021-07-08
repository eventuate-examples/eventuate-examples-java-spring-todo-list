#! /bin/bash

export database=postgres
export mode=polling

./_build-and-test-all.sh  $* -P eventuateDriver=local
