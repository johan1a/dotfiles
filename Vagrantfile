# -*- mode: ruby -*-
# vi: set ft=ruby :

# All Vagrant configuration is done below. The "2" in Vagrant.configure
# configures the configuration version (we support older styles for
# backwards compatibility). Please don't change it unless you know what
# you're doing.
Vagrant.configure("2") do |config|
  # Every Vagrant development environment requires a box. You can search for
  # boxes at https://atlas.hashicorp.com/search.
  config.vm.box = "ubuntu/xenial64"
  config.vm.provision "shell", inline: "sudo apt-get install git"
  config.vm.provision "shell", inline: "git clone https://github.com/johan1a/dotfiles.git"
  config.vm.provision "shell", 
    inline: "sed -i -e 's/johan/ubuntu/g' /home/ubuntu/dotfiles/ansible/group_vars/local.yml"
  config.vm.provision "shell", inline: "cd /home/ubuntu/dotfiles && sh /home/ubuntu/dotfiles/bootstrap.sh",  env: {"Vagrant" => "True"}
  config.vm.provision "shell", inline: "echo 'ubuntu:ubuntu' | chpasswd"
  config.vm.provision "shell", inline: "reboot"
end
