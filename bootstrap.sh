#!/bin/bash

NAME_STR="$(head -n 1 /etc/os-release)"
OS="$(echo ${NAME_STR} | sed 's/NAME=//g')"

if [[ "${OS}" == *"Ubuntu"* ]] ; then
  sudo apt-get update
  sudo apt-get install libssl-dev python-pip -y
elif [[ "${OS}" == *"Arch Linux"* ]] ; then
  sudo pacman -Syu --noconfirm
  sudo pacman -S python-pip --noconfirm
fi

# sudo pip install --upgrade pip
sudo pip install ansible
ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --become
