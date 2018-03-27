#! /bin/bash -e

set -e
set -o pipefail

SCRIPTS="single-module/build-and-test-all.sh
single-module/build-and-test-all-eventuate-local.sh
single-module/build-and-test-all-eventuate-local-postgres.sh
multi-module/build-and-test-all.sh
multi-module/build-and-test-all-eventuate-local.sh
multi-module/build-and-test-all-eventuate-local-postgres.sh
"
date > build-and-test-everything.log

for script in $SCRIPTS ; do
   echo '****************************************** Running' $script
   date >> build-and-test-everything.log
   echo '****************************************** Running' $script >> build-and-test-everything.log

   (cd $(dirname $script) ; ./$(basename $script) ) | tee -a build-and-test-everything.log
done

echo 'Finished successfully!!!'
