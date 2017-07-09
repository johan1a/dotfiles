#!/bin/bash

sudo apt-get update \
  && sudo apt-get install python-pip -y \
  && pip install --upgrade pip \
  && sudo apt-get install ansible=2.0.0.2-2 \
  && ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --sudo
#  && sudo pip install ansible==2.0.0.2 \
