# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # replace Windows line endings with Unix line endings
  config.vm.box = "archlinux/archlinux"

  config.vm.provider "virtualbox" do |v|
       v.customize ["modifyvm", :id, "--vram", "256"]
       v.memory = 4096
       v.gui = true
  end

  config.vm.synced_folder "dev", "/home/vagrant/dev"

  config.vm.provision "shell" do |s|
    s.binary = true
    s.inline = "
      sudo pacman -Syu --noconfirm;
      sudo pacman -S git dos2unix --noconfirm;
      if [ -d 'dotfiles' ]; then rm -rf dotfiles; fi;
      cp -r /vagrant dotfiles;
      sed -i -e 's/johan/vagrant/g' /home/vagrant/dotfiles/ansible/group_vars/local.yml;
      sudo find . -type f -print0 | xargs -0 dos2unix;
      VAGRANT=True cd /home/vagrant/dotfiles && /home/vagrant/dotfiles/bootstrap.sh;
      echo 'vagrant:vagrant' | chpasswd;
      xrandr -s 1920x1080;"
  end
  

  #config.vm.provision "shell", inline: "reboot"
end
