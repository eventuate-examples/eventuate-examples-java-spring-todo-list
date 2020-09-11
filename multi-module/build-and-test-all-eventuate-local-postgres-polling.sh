#! /bin/bash

export database=postgres
export mode=wal

./_build-and-test-all.sh  $* -P eventuateDriver=local
