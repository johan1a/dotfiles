
set fish_greeting
set -x VISUAL nvim
set -x ANSIBLE_NOCOWS 1

if status --is-interactive;
    [ -e $HOME/.aliases.sh ]; and . $HOME/.aliases.sh
end

# Base16 Shell
if status --is-interactive
  source $HOME/.config/base16-shell/profile_helper.fish
end

source ~/.config/fish/functions/utils.fish
[ -e $HOME/.local.fish ]; and source $HOME/.local.fish

alias sf="source ~/.config/fish/config.fish"

# Start X at login
if status is-login
    if test -z "$DISPLAY" -a $XDG_VTNR = 1
        exec startx -- -keeptty
    end
end

