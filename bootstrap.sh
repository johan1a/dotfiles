#!/bin/bash -e

PASSWORD=$1

if [ -z $PASSWORD ] ; then
  echo "Enter SUDO password: "
  read -s PASSWORD
fi

NAME_STR="$(head -n 1 /etc/os-release)"
OS="$(echo ${NAME_STR} | sed 's/NAME=//g')"

if [[ "${OS}" == *"Ubuntu"* ]] ; then
  echo $PASSWORD | sudo -S  apt-get update
  echo $PASSWORD | sudo -S  apt-get install libssl-dev python-pip -y
  pip install --upgrade --user pip
  pip install --user ansible
elif [[ "${OS}" == *"Arch Linux"* ]] ; then
  echo $PASSWORD | sudo -S pacman -Syu --noconfirm --needed
  echo $PASSWORD | sudo -S pacman -S tree gcc which leiningen python-pip ansible --noconfirm --needed
fi

cd tfconfig
lein run --password $PASSWORD --user $USER --verbose
cd ..

ansible-playbook -e "ansible_become_pass=$PASSWORD" ansible/bootstrap.yml -i ansible/hosts.ini --become
