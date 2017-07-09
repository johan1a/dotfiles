#!/bin/bash

sudo apt-get update \
  && sudo pip install ansible \
  && cd ansible \
  && ansible-playbook bootstrap.yml -i hosts.ini --sudo
