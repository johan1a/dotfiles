#!/bin/bash

sudo apt-get update \
  && sudo apt-get install python-pip -y
  && pip install --upgrade pip
  && sudo pip install ansible \
  && ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --sudo
