#!/bin/bash

# For neovim
sudo apt-get install software-properties-common -y \
 && sudo add-apt-repository ppa:neovim-ppa/stable \
 && sudo apt-get update

apt-get install -y rxvt-unicode-256color \
git  \
tmux \
neovim \
tree \
python3-pip \
python-pip \
ruby-dev \
wmctrl \
docker \
dh-autoreconf # For universal-ctags

# Bind fullscreen to F11: 
wmctrl -r ":ACTIVE:" -b toggle,fullscreen

# Install universal ctags
git clone https://github.com/universal-ctags/ctags.git ctags_install \
  && cd ctags_install \
  && ./autogen.sh \
  && ./configure \
  && make \
  && sudo make install

git clone https://github.com/chriskempson/base16-shell.git ~/.config/base16-shell \
 && git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim

pip3 install --upgrade pip \
 && pip3 install --upgrade neovim \
 && pip install --upgrade pip \
 && sudo pip2 install --upgrade neovim \
 && sudo gem install neovim

sudo gpasswd -a $USER docker
newgrp docker

sudo ln -s ~/dotfiles/makesymlinks.sh /usr/local/bin/makesymlinks
makesymlinks

