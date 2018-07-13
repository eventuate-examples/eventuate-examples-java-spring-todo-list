#! /bin/bash -e

set -e
set -o pipefail

SCRIPTS="single-module/build-and-test-all-eventuate-local-mysql.sh
single-module/build-and-test-all-eventuate-local-postgres-polling.sh
single-module/build-and-test-all-eventuate-local-postgres-wal.sh
multi-module/build-and-test-all-eventuate-local-mysql.sh
multi-module/build-and-test-all-eventuate-local-postgres-polling.sh
multi-module/build-and-test-all-eventuate-local-postgres-wal.sh
"

date > build-and-test-everything.log

for script in $SCRIPTS ; do
   echo '****************************************** Running' $script
   date >> build-and-test-everything.log
   echo '****************************************** Running' $script >> build-and-test-everything.log

   (cd $(dirname $script) ; ./$(basename $script) ) | tee -a build-and-test-everything.log
done

echo 'Finished successfully!!!'
