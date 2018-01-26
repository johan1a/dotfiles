
set fish_greeting
set -x VISUAL vim

if status --is-interactive;
    [ -e $HOME/.aliases.sh ]; and . $HOME/.aliases.sh
end

# Base16 Shell
if status --is-interactive
   source $HOME/.config/base16-shell/profile_helper.fish
end

source ~/.config/fish/functions/utils.fish

alias sf="source ~/.config/fish/config.fish"
alias c=cd
