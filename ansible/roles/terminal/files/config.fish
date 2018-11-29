
set fish_greeting
set -x VISUAL nvim
set -x ANSIBLE_NOCOWS 1
set -x FZF_DEFAULT_COMMAND 'rg --files --hidden --follow -g "!{.git,node_modules}/*" 2> /dev/null'
set -x FZF_CTRL_T_COMMAND $FZF_DEFAULT_COMMAND
set -x BROWSER google-chrome-stable



if status --is-interactive;
    [ -e $HOME/.aliases.sh ]; and . $HOME/.aliases.sh
end


source ~/.config/fish/functions/utils.fish
[ -e $HOME/.local.fish ]; and source $HOME/.local.fish

if status --is-interactive; and [ "$SSH_CLIENT" = "" ]
  source $HOME/.config/base16-shell/profile_helper.fish
end

alias sf="source ~/.config/fish/config.fish"

# Start X at login
if status is-login
    if test -z "$DISPLAY" -a $XDG_VTNR = 1
        exec startx -- -keeptty
    end
end

