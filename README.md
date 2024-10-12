
# dotfiles

[![pipeline status](https://gitlab.com/johan1a/dotfiles/badges/master/pipeline.svg)](https://gitlab.com/johan1a/dotfiles/commits/master)

Automated Arch Linux system configuration.

## How to run

```
chmod +x install.sh

./install.sh

# Run specific modules
./install.sh --modules pipewire,neovim

# or
./install.sh -m pipewire,neovim

# After it has run at least once, you can install from any directory using:
s dotfiles install

# Run specific modules only:
s dotfiles run modules pipewire,neovim
```
