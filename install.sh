#!/bin/sh

echo "Enter username: "
read username

echo "Enter password: "
read -s password

timedatectl set-ntp true

parted --script /dev/sda mklabel msdos mkpart primary ext4 0% 100%
mkfs.ext4 /dev/sda1
mount /dev/sda1 /mnt

# pacstrap
pacstrap /mnt base git sudo

# fstab
genfstab -U /mnt >> /mnt/etc/fstab
echo "org /home/$user/dev vboxsf uid=$user,gid=wheel,rw,dmode=700,fmode=600,nofail 0 0" >> /mnt/etc/fstab

# chroot
wget https://gitlab.com/johan1a/dotfiles/raw/master/chroot-install.sh /mnt/chroot-install.sh -O /mnt/chroot-install.sh
arch-chroot /mnt /bin/bash ./chroot-install.sh $user $password

# reboot
umount /mnt
reboot
