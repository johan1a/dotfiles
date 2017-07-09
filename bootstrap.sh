#!/bin/bash

sudo apt-get update \
  && sudo apt-get install libssl-dev python-pip -y \
  && pip install --upgrade pip \
  && sudo pip install ansible \
  && ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --sudo
#  && sudo pip install ansible==2.0.0.2 \
