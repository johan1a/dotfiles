set fish_greeting
set -x VISUAL nvim
set -x ANSIBLE_NOCOWS 1
set -x FZF_DEFAULT_COMMAND 'rg --files --hidden --follow -g "!{.git,node_modules}/*" 2> /dev/null'
set -x FZF_CTRL_T_COMMAND $FZF_DEFAULT_COMMAND
set -x FZF_CTRL_T_OPTS "--preview 'bat --color=always --style=numbers --line-range=:500 {}'"
set -x BROWSER firefox
set -x WINEARCH win64
set -x WINEPREFIX /home/$USER/.wine64
set -x GRAALVM_HOME /usr/lib/jvm/java-11-graalvm/
ulimit -S -n 6000

export XDG_CONFIG_HOME="$HOME/.config"
export XDG_DESKTOP_DIR="$HOME/"
export XDG_DOCUMENTS_DIR="$HOME/documents/"
export XDG_DOWNLOAD_DIR="$HOME/downloads/"
export XDG_MUSIC_DIR="$HOME/music"
export XDG_PICTURES_DIR="$HOME/pictures"
export XDG_VIDEOS_DIR="$HOME/videos"
export COURSIER_CACHE="$XDG_DOWNLOAD_DIR/.coursier-cache"
export CLOUDSDK_PYTHON=/usr/bin/python3


contains /home/$USER/.local/bin/ $fish_user_paths; or set -Ua fish_user_paths /home/$USER/.local/bin/
contains /home/$USER/go/bin/ $fish_user_paths; or set -Ua fish_user_paths /home/$USER/go/bin/
contains /opt/google-cloud-cli/bin/ $fish_user_paths; or set -Ua fish_user_paths /opt/google-cloud-cli/bin/
contains /home/$USER/.local/share/coursier/bin/ $fish_user_paths; or set -Ua fish_user_paths /home/$USER/.local/share/coursier/bin/

source ~/.config/fish/functions/utils.fish

[ -e $HOME/.local.fish ]; and source $HOME/.local.fish

alias vim=nvim

if status --is-interactive; and [ "$SSH_CLIENT" = "" ]
  eval sh '"'(realpath ~/.base16_theme)'"'
end

alias sf="source ~/.config/fish/config.fish"

# Start X at login
if status is-login
    if test -z "$DISPLAY" -a "$XDG_VTNR" = 1
        exec startx -- -keeptty
    end
end

# pnpm
set -gx PNPM_HOME "/home/johan/.local/share/pnpm"
if not string match -q -- $PNPM_HOME $PATH
  set -gx PATH "$PNPM_HOME" $PATH
end
# pnpm end
