#! /bin/bash

export database=mysql
export mode=binlog

./_build-and-test-all.sh  $* -P eventuateDriver=local
