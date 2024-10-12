#!/bin/sh

echo "ci" > /etc/hostname
pacman -Syu --noconfirm
pacman -S sudo git --noconfirm
useradd johan1a
usermod -aG wheel johan1a
echo "johan1a   ALL=(ALL) ALL" >> /etc/sudoers
echo "Defaults:johan1a      !authenticate" >> /etc/sudoers
mkdir /home/johan1a
chown "johan1a:" /home/johan1a -R

sudo -u johan1a bash -c 'export CI=True; ./install.sh --password hunter2 --config ../config.yaml'
