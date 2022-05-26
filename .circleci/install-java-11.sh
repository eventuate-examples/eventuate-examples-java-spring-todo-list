#! /bin/bash -e

export SDKMAN_DIR=/home/circleci/.sdkman
curl -s "https://get.sdkman.io" | bash
source "$SDKMAN_DIR/bin/sdkman-init.sh"
sdk install java $(sdk list java | egrep " 11.*zulu" | head -1 | cut -f6 -d\|)
