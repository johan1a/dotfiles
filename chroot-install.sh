#!/bin/sh -e

user=$1
password=$2

# setup timezone
echo 'Setting up timezone'
rm /etc/localtime
# timedatectl set-ntp true
ln -s /usr/share/zoneinfo/Europe/Stockholm /etc/localtime
# timedatectl set-timezone Europe/Stockholm
hwclock --systohc

# setup locale
echo 'Setting up locale'
sed -i 's/^#en_US.UTF-8/en_US.UTF-8/' /etc/locale.gen
locale-gen
echo 'LANG=en_US.UTF-8' > /etc/locale.conf

# setup hostname
echo 'Setting up hostname'
echo 'arch-virtualbox' > /etc/hostname

echo 'Running mkinitcpio'
mkinitcpio -p linux

# install bootloader
echo 'Installing bootloader'
pacman -S grub --noconfirm
grub-install --target=i386-pc /dev/sda
grub-mkconfig -o /boot/grub/grub.cfg

# install virtualbox guest modules
echo 'Installing VB-guest-modules'
pacman -S --noconfirm virtualbox-guest-modules-arch virtualbox-guest-utils

echo 'vboxsf' > /etc/modules-load.d/vboxsf.conf

echo 'Setting up user'
echo 'Creating user'
useradd -m -G wheel -s /usr/bin/bash $user
echo 'Setting password'
echo $user:$password | chpasswd
echo root:$password | chpasswd
echo '%wheel ALL=(ALL) ALL' >> /etc/sudoers

#systemctl enable ntpdate.service

systemctl enable dhcpcd
systemctl start dhcpcd


cd /home/$user
echo $password | sudo -S -u johan git clone https://gitlab.com/johan1a/dotfiles.git
cd dotfiles
chmod +x bootstrap.sh
echo $password | sudo -S -u johan ./bootstrap.sh $password

exit
exit
