#!/bin/bash -e

usage() {
  echo "Usage: $0 --password <password> --config <config-file>"
  exit 1
}

CONFIG="../config.yaml"
VERBOSE="true"

while [[ "$#" -gt 0 ]]; do
  case $1 in
    --password)
      PASSWORD="$2"
      shift 2
      ;;
    --config)
      CONFIG="$2"
      shift 2
      ;;
    --modules)
      MODULES="$2"
      shift 2
      ;;
    -m)
      MODULES="$2"
      shift 2
      ;;
    --verbose)
      VERBOSE="$2"
      shift 2
      ;;
    *)
      echo "Unknown parameter: $1"
      usage
      ;;
  esac
done

if [ -z $PASSWORD ] ; then
  echo "Enter SUDO password: "
  read -s PASSWORD
fi

if command -v pacman &> /dev/null && ! command -v lein &> /dev/null;
then
  echo $PASSWORD | sudo -S pacman -Syu --noconfirm --needed
  echo $PASSWORD | sudo -S pacman -S inetutils which leiningen --noconfirm --needed
elif command -v brew &> /dev/null && ! command -v lein &> /dev/null;
then
    brew install leiningen
elif command -v apt &> /dev/null && ! command -v lein &> /dev/null;
then
  echo $PASSWORD | sudo -S apt-get update -y
  echo $PASSWORD | sudo -S apt-get install leiningen -y
fi

if [ "$VERBOSE" = "true" -o -n "$CI" ] ; then
  export VERBOSE_FLAG=--verbose
fi

echo Using config: $CONFIG

cd tfconfig
lein run --config $CONFIG --password $PASSWORD --user $USER $VERBOSE_FLAG --modules $MODULES
cd ..
