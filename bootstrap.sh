#!/bin/bash

sudo apt update \
  && sudo apt install git ansible -y \
  && cd ansible \
  && ansible-playbook -b bootstrap.yml -i hosts.ini --sudo
