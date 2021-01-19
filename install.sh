#!/bin/bash -e

PASSWORD=$1

if [ -z $PASSWORD ] ; then
  echo "Enter SUDO password: "
  read -s PASSWORD
fi

echo $PASSWORD | sudo -S pacman -Syu --noconfirm --needed
echo $PASSWORD | sudo -S pacman -S inetutils which leiningen --noconfirm --needed

if [ -z $CI ] ; then
  export VERBOSE_FLAG=
else
  export VERBOSE_FLAG=--verbose
fi

cd tfconfig
lein run --password $PASSWORD --user $USER $VERBOSE_FLAG
cd ..
