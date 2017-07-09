#!/bin/bash

sudo apt-get update \
  && sudo pip install ansible \
  && ansible-playbook ansible/bootstrap.yml -i ansible/hosts.ini --sudo
