#!/bin/bash -e

PASSWORD=$1
CONFIG=$2

if [ -z $PASSWORD ] ; then
  echo "Enter SUDO password: "
  read -s PASSWORD
fi

if [ -z $CONFIG ] ; then
  export CONFIG=../config.yaml
fi

if not pacman --query --quiet inetutils which leiningen &> /dev/null
then
  echo $PASSWORD | sudo -S pacman -Syu --noconfirm --needed
  echo $PASSWORD | sudo -S pacman -S inetutils which leiningen --noconfirm --needed
fi

if [ -z $CI ] ; then
  export VERBOSE_FLAG=
else
  export VERBOSE_FLAG=--verbose
fi

echo Using config: $CONFIG

cd tfconfig
lein run --config $CONFIG --password $PASSWORD --user $USER $VERBOSE_FLAG --modules $MODULES
cd ..
