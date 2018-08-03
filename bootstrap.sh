#!/bin/bash

NAME_STR="$(head -n 1 /etc/os-release)"
OS="$(echo ${NAME_STR} | sed 's/NAME=//g')"

if [[ "${OS}" == *"Ubuntu"* ]] ; then
  sudo apt-get update
  sudo apt-get install libssl-dev python-pip -y
elif [[ "${OS}" == *"Arch Linux"* ]] ; then
  sudo pacman -Syu --noconfirm
  sudo pacman -S gcc python-pip --noconfirm
fi

echo 11111
pip install --upgrade --user pip
echo 22222
sudo pip install ansible
echo 33333
ls -l ~/.local/bin
echo 44444
ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --become
