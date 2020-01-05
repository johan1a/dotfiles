set fish_greeting
set -x VISUAL nvim
set -x ANSIBLE_NOCOWS 1
set -x FZF_DEFAULT_COMMAND 'rg --files --hidden --follow -g "!{.git,node_modules}/*" 2> /dev/null'
set -x FZF_CTRL_T_COMMAND $FZF_DEFAULT_COMMAND
set -x BROWSER google-chrome-stable

export XDG_CONFIG_HOME="$HOME/.config"
export XDG_DESKTOP_DIR="$HOME/"
export XDG_DOCUMENTS_DIR="$HOME/documents/"
export XDG_DOWNLOAD_DIR="$HOME/downloads/"
export XDG_MUSIC_DIR="$HOME/music"
export XDG_PICTURES_DIR="$HOME/pictures"
export XDG_VIDEOS_DIR="$HOME/videos"
export COURSIER_CACHE="$XDG_DOWNLOAD_DIR/.couries-cache"

source ~/.config/fish/functions/utils.fish

[ -e $HOME/.local.fish ]; and source $HOME/.local.fish

if status --is-interactive;
    preset-password
end

if command -v nvim > /dev/null 2>&1;
  alias vim=nvim
end

if status --is-interactive; and [ "$SSH_CLIENT" = "" ]
  eval sh '"'(realpath ~/.base16_theme)'"'
end

alias sf="source ~/.config/fish/config.fish"

# Start X at login
if status is-login
    if test -z "$DISPLAY" -a $XDG_VTNR = 1
        exec startx -- -keeptty
    end
end

