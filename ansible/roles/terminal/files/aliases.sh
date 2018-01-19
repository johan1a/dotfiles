
# Find auto completion:
# complete -p git



alias vim=nvim
alias v=nvim

alias g='git'
#complete -o bashdefault -o default -o nospace -F _git g

alias ga='git add'
alias gc='git commit -m '
alias gd='git diff'
alias gs='git status -s'
alias gl='git lg'
alias gb='git branch -v'
alias gp='git pull --rebase'

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
alias f="find . -name "
alias ta="tmux attach -t "
alias td="tmux detach"
alias tl="tmux list-sessions"
#alias t="tmux"
alias trs='tmux rename-session'
alias a="ag"
alias pj="python -m json.tool"
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
alias p=pwd
alias d="docker"
alias dps="docker ps "
alias dt="docker start "
alias dp="docker stop "
alias dr="docker restart "
alias rclocal="sudo /etc/rc.local"
alias fconfig="cd ~/.config/fish/"
alias cl=clear
alias y=yaourt
alias ys="yaourt -S "
alias yss="yaourt -Ss "
alias less="less -S"
alias vv="vim ~/.vimrc"
alias executable="chmod +x "
alias list-jks="keytool -list -v -keystore "
alias printcert="keytool -printcert -v -file "
