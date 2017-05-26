#!/bin/bash
############################
# .make.sh
# This script creates symlinks from the home directory to any desired dotfiles in ~/dotfiles
############################

########## Variables

dir=~/dotfiles                    # dotfiles directory
olddir=~/dotfiles_old             # old dotfiles backup directory
files="gitconfig tmux.conf bashrc bash_aliases inputrc vimrc vim Xresources" #i3 i3status.conf"    # list of files/folders to symlink in homedir

##########

# create dotfiles_old in homedir
echo "Creating $olddir for backup of any existing dotfiles in ~/"
mkdir -p "$olddir"
echo "...done"

# change to the dotfiles directory
echo "Changing to the $dir directory"
cd "$dir"
echo "...done"

rm_if_link(){ [ ! -L "$1" ] || rm -v "$1"; }

# move any existing dotfiles in homedir to dotfiles_old directory, then create symlinks 
echo "Moving any existing dotfiles from ~ to $olddir"
for file in $files; do
    echo "Updating .$file"
    rm -rf $olddir/.$file
    mv -f ~/.$file $olddir/
    ln -s "$dir/$file" ~/.$file
done

mkdir -p ~/.config
mv ~/.config/nvim $olddir/ # "backup nvim config in case it is not a symlink to vimrc."
mv ~/.vim/init.vim $olddir/
echo "Linking nvim config to vim config"
ln -s ~/.vim ~/.config/nvim 
ln -s "$dir/vimrc" ~/.vim/init.vim

echo "...done!"
