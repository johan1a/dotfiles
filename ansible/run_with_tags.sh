#!/bin/sh
ansible-playbook bootstrap.yml -i hosts.ini -K --tags $1
