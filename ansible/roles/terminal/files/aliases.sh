
# Find auto completion:
# complete -p git
alias ..2="cd ../.."
alias ..3="cd ../../.."
alias ..4="cd ../../../.."
alias ..5="cd ../../../../.."
alias ..6="cd ../../../../../.."
alias ..7="cd ../../../../../../.."
alias ..8="cd ../../../../../../../.."
alias ..9="cd ../../../../../../../../.."
alias ..="cd .."
alias a="ag"
alias amend='git commit --amend'
alias cf="vim ~/.config/fish"
alias ci3="vim ~/.config/i3/config"
alias cl=clear
alias con="./gradlew codenarcAll"
alias cv="vim ~/.vimrc"
alias d="docker"
alias dev="cd ~/dev"
alias dotfiles="cd ~/dotfiles"
alias dp="docker stop "
alias dps="docker ps "
alias dr="docker restart "
alias ds="trans da:sv "
alias dt="docker start "
alias e=exit
alias executable="chmod +x "
alias f="find . -name "
alias fconfig="cd ~/.config/fish/"
alias futils="vim ~/.config/fish/functions/utils.fish"
alias g='git'
alias ga='git add'
alias gb='git branch -v'
alias gc='git commit -m '
alias gd='git diff'
alias gdd='git diff --cached'
alias gl='git lg'
alias gp='git pull --rebase'
alias gs='git status -s'
alias gt='./gradlew test'
alias intercept="strace -ff -e trace=write -e write=1,2 -p "
alias l='ls -alF'
alias la='ls -A'
alias less="less -S"
alias list-jks="keytool -list -v -keystore "
alias ll='ls -alF'
alias lt='ls -ltr'
alias p=pwd
alias pa='ps ax | a'
alias package='dpkg -S /usr/bin/ls'
alias pj="python -m json.tool"
alias print-keymap="xkbcomp -xkb $DISPLAY my_xkb_keymap"
alias printcert="keytool -printcert -v -file "
alias r=rg
alias rclocal="sudo /etc/rc.local"
alias readram="sudo dd if=/dev/mem | cat | strings"
alias s=sudo
alias sd="trans sv:da "
alias sdkman-init='source ~/.sdkman/bin/sdkman-init.sh'
alias serve='python -m SimpleHTTPServer' # optional arg: port (defaults to 8000)
alias t=cat
alias ta="tmux attach -t "
alias td="tmux detach"
alias tl="tmux list-sessions"
alias tlk='tmux list-keys'
alias tlc='tmux list-commands'
alias ti='tmux info'
alias trs='tmux rename-session'
alias trw='tmux rename-window'
alias ts='tmux switch -t'
alias v=nvim
alias vim=nvim
alias vim-debug='nvim -V9vim-debug.log'
alias xc="xclip -selection clipboard "
alias y=yaourt
alias youtube-mp3='youtube-dl -t --extract-audio --audio-format mp3 '
alias ys="yaourt -S "
alias yss="yaourt -Ss "
