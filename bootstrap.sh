#!/bin/bash

sudo apt-get update \
  && sudo apt-get install git ansible -y \
  && cd ansible \
  && ansible-playbook bootstrap.yml -i hosts.ini --sudo
