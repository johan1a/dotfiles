#!/bin/bash

PASSWORD=$1

if [ -z $PASSWORD ] ; then
  echo "Enter sudo password: "
  read -s PASSWORD
fi

NAME_STR="$(head -n 1 /etc/os-release)"
OS="$(echo ${NAME_STR} | sed 's/NAME=//g')"

echo $PASSWORD | sudo -S ls > /dev/null

if [[ "${OS}" == *"Ubuntu"* ]] ; then
  echo $PASSWORD | sudo -S  apt-get update
  echo $PASSWORD | sudo -S  apt-get install libssl-dev python-pip -y
  pip install --upgrade --user pip
  pip install --user ansible
elif [[ "${OS}" == *"Arch Linux"* ]] ; then
  echo $PASSWORD | sudo -S pacman -Syu --noconfirm
  echo $PASSWORD | sudo -S pacman -S gcc python-pip ansible --noconfirm
fi

ansible-playbook -e "ansible_become_pass=$PASSWORD" ansible/bootstrap.yml -i ansible/hosts.ini --become
