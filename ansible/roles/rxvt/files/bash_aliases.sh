# enable color support of ls and also add handy aliases
if [ -x /usr/bin/dircolors ]; then
    test -r ~/.dircolors && eval "$(dircolors -b ~/.dircolors)" || eval "$(dircolors -b)"
    alias ls='ls --color=auto'
    #alias dir='dir --color=auto'
    #alias vdir='vdir --color=auto'

    alias grep='grep --color=auto'
    alias fgrep='fgrep --color=auto'
    alias egrep='egrep --color=auto'
fi


alias vim=nvim
alias v=nvim
alias upgrade='sudo apt update && sudo apt upgrade -y'
alias g='git'
alias ga='git add'
alias gc='git commit'
alias gd='git diff'
alias gs='git status'
alias gl='git lg'
alias amend='git commit --amend'
alias sdkman-init='source ~/.sdkman/bin/sdkman-init.sh'
alias ..="cd .."
alias ..2="cd ../.."
alias ..3="cd ../../.."
alias ..4="cd ../../../.."
alias ..5="cd ../../../../.."
alias ..6="cd ../../../../../.."
alias ..7="cd ../../../../../../.."
alias ..8="cd ../../../../../../../.."
alias ..9="cd ../../../../../../../../.."
alias colemak="setxkbmap us -variant colemak && echo Switched to colemak"
alias swedish="setxkbmap se && echo Switched to Swedish \(QWERTY\)"
alias danish="setxkbmap dk && echo Switched to Danish \(QWERTY\)"
alias f="find . -name "
alias ta="tmux attach -t "
alias td="tmux detach"
alias tl="tmux list-sessions"
alias sb="source ~/.bashrc && echo ...sourced ~/.bashrc"
alias x="xrdb -merge ~/.Xresources && echo xrdb -merge ~/.Xresources "
alias a="ag"
alias pj="python -m json.tool"
alias cdtmp='cd $(mktemp -d ~/temp/$USER-cdtmp-XXXXXX)'
alias serve='python -m SimpleHTTPServer' # optional arg: port (defaults to 8000)
alias dotfiles="cd ~/dotfiles"
alias dev="cd ~/dev"
alias e=exit
alias ll='ls -alF'
alias la='ls -A'
alias l='ls -alF'
alias s=sudo
alias readram="sudo dd if=/dev/mem | cat | strings"
alias intercept="strace -ff -e trace=write -e write=1,2 -p "
alias pa='ps ax | a'
alias package='dpkg -S /usr/bin/ls'
alias youtube-mp3='youtube-dl -t --extract-audio --audio-format mp3 '

# Add an "alert" alias for long running commands.  Use like so:
#   sleep 10; alert
alias alert='notify-send --urgency=low -i "$([ $? = 0 ] && echo terminal || echo error)" "$(history|tail -n1|sed -e '\''s/^\s*[0-9]\+\s*//;s/[;&|]\s*alert$//'\'')"'

# Alias definitions.
# You may want to put all your additions into a separate file like
# ~/.bash_aliases, instead of adding them here directly.
# See /usr/share/doc/bash-doc/examples in the bash-doc package.
