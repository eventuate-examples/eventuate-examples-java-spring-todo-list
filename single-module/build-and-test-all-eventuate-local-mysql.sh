#! /bin/bash

. ./set-env-mysql.sh

export EXTRA_INFRASTRUCTURE_SERVICES=mysqlbinlogcdc
export EVENTUATE_LOCAL=yes
export database=mysql
export mode=binlog

./_build-and-test-all.sh $* -P eventuateDriver=local
