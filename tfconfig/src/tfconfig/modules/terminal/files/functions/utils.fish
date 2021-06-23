
function get_git_branch
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'
end

function git_is_dirty
    git status > /dev/null
    git diff-index --quiet HEAD --;
    if [ $status = 0 ]
      echo False
    else
      echo True
    end
end

function mc
    mkdir -p $argv; and cd $argv
end

function ml
    cd $argv; and ll
end

# cd to newest
function cn
    set newest (ls -tr | tail -n 1)
    cd $newest
end

function backup
    cp -r "$argv"{,.bak};
end

function google
    google-chrome-stable "http://www.google.com/search?q=$argv"
end

function mostused
    history | awk '{print $1}' | sort  | uniq --count | sort --numeric-sort --reverse | head -10
end

function wttr
    curl "wttr.in/$argv"
end

function grab
    sudo chown $user -R $argv
end

function usblogin
    /media/johan/DTVP30/linux/linux64/dtvp_login
end

function usblogout
    /media/johan/DTVP30/linux/linux64/dtvp_logout
end

function kingston
    cd /media/johan/KINGSTON
end

function fixaltgr
    xmodmap -e 'keycode 108 = Hyper_R'; and xmodmap -e 'add mod3 = Hyper_R'
end

function sync-config
    set prev_dir (pwd)
    cd ~/dotfiles
    ./bootstrap.sh
    cd $prev_dir
end

function sync-with-tags
    set prev_dir (pwd)
    cd ~/dotfiles/ansible
    ./run_with_tags.sh $argv
    cd $prev_dir
end

function swt
    sync-with-tags $argv
end

# Mysterious black magic spell.
# Some say these ancient words have the power to fix
# even the worst cases of Dell latitude CPU throttling.
# But who knows at what cost? Use at your own risk...
function black-magic-spell
    sudo rdmsr 0x1fc
    sudo wrmsr 0x1fc f84
end

function reload-i3
    i3-msg reload
end

function ssh
    set TERM xterm
    /usr/bin/ssh $argv
end

function scp
    set TERM xterm
    /usr/bin/scp $argv
end

function todo
    vim ~/documents/todo.txt
end

function danish
    setxkbmap dk
    echo "Switched to Danish (QWERTY)."
end

function swedish
    echo "Switched to Swedish (QWERTY)."
    setxkbmap se
end

function compress
    tar -zcvf $argv.tar.gz $argv
end

function upgrade
    sudo apt update
    sudo apt upgrade
    sudo apt autoremove -y
    pip2 list | tail -n +3 | awk  ' {print $1}' | xargs sudo pip2 install -U
    pip3 list | tail -n +3 | awk  ' {print $1}' | xargs sudo pip3 install -U
end

function usb-backup
    echo backing up files to usb drive...
    usblogin
    set backup_dir /media/johan/KINGSTON/backup/
    rsync -r --progress ~/documents $backup_dir
    rsync --progress johan.kdbx $backup_dir
    echo backup done.
end

function usb-restore
    echo restoring files to usb drive...
    usblogin
    set backup_dir /media/johan/KINGSTON/
    set dest_dir ~/KINGSTON/
    rsync -r --progress $backup_dir $dest_dir
    echo restore done.
end

function current-theme
    readlink ~/.base16_theme
end

function sdk
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk $argv"

    for ITEM in $HOME/.sdkman/candidates/* ;
        set -gx PATH $PATH $ITEM/current/bin
    end
end

function cloneg
  git clone git@github.com:$argv.git
end

function clone
  git clone git@gitlab.com:$argv.git
end

function gcl
  git config -l $argv
end

function gb
  git branch -v $argv
end

function pull-dir
  set dir $argv
  if test -d $dir
    cd $dir
    if test -d .git
      if [ (git_is_dirty) = "False" ]
        echo "Pulling $dir"
        git pull --rebase >/dev/null 2>&1
      else
        echo "Not pulling dirty dir: $dir"
        git fetch --all >/dev/null 2>&1
      end
    end
    cd ..
  end
end

function wait-for-jobs
  while fg | grep -qv "There are no suitable jobs"; end
end

function get-nbr-children
  ps -o pid --ppid $argv --noheaders | wc -l
end

function wait-for-children
  set parent_pid (python3 -c "import os; print(os.getppid())")
  set nbr_children (get-nbr-children $parent_pid)
  while [ $nbr_children -gt 2 ] ;
    sleep 0.2
    set nbr_children (get-nbr-children $parent_pid)
  end
end

function background
  fish -c "$argv" &
end

function pull-all
  echo Pulling non-dirty git-projects...
  echo ""

  set dirs (ls)
  for dir in $dirs
    background pull-dir $dir
  end

  wait-for-children

  echo Done!
end

function mass-checkout
  echo Checking out master for all non-dirty git-projects...
  echo ""

  set dirs (ls)
  for dir in $dirs
    if not test -d $dir
      continue
    end

    cd $dir

    if not test -d .git
      cd ..
      continue
    end

    echo ""
    if [ (git_is_dirty) = "False" ]
      echo Pulling (pwd)
      git checkout $argv
    else
      echo (pwd) is dirty, leaving as it is.
    end
    cd ..
  end

  echo Done!
end

function preset-password

  set keygrip 4F84C5677466E4DF3CCDFB177F15CE6C3F299895

  set cached (echo "GET_PASSPHRASE --no-ask $keygrip Err Pmt Des" | gpg-connect-agent)

  switch "$cached"
    case "ERR*"
    # Requires secret-tool store --label "Mbsync password" id mbsync-password
    set password (secret-tool lookup id mbsync-password)
    echo "$password" | /usr/lib/gnupg/gpg-preset-passphrase --preset $keygrip
  end
end

function neomutt
  mbsync-launcher > /dev/null 2>&1 &
  command neomutt
end

function reload-gpg
  gpg-connect-agent reloadagent /bye
end

function keyboard-current
  setxkbmap -print -verbose 10
end

function keyboard-available
  less /usr/share/X11/xkb/rules/base.lst
end

function gsp
  git stash
  git pull --rebase
  git stash pop
end

function grails
  command which grails > /dev/null
  if test $status -ne 0
    sdk > /dev/null
  end
  command grails $argv
end

function vagrant
  set TERM xterm
  command vagrant $argv
end

function edit-xclip
  xclip -o | vipe | xclip
end

function edit-xclip-file
  set initial_workspace (i3-current-workspace)
  i3-msg workspace clipboard
  i3-msg focus parent
  i3-msg kill

  i3-msg workspace $initial_workspace
  i3-msg move container to workspace clipboard
  i3-msg workspace clipboard
  echo $initial_workspace
  set temp_file /tmp/clipboard.txt
  touch $temp_file
  xclip -o > $temp_file
  nvim $temp_file
  cat $temp_file | xclip -selection clipboard
  cat $temp_file | xclip -selection primary
  i3-msg workspace $initial_workspace
end

function i3-current-workspace
  i3-msg -t get_workspaces | python -c "import sys, json; print(next(x for x in json.load(sys.stdin) if x['focused'])['num'])"
end

function nuke-docker
  echo Stopping and removing all containers...
  docker stop (docker ps -aq)
  docker rm (docker ps -aq)
end

function os-name
  set NAME_STR (head -n 1 /etc/os-release)
  set OS (echo $NAME_STR | sed 's/NAME=//g')
  echo $OS
end

function to-txt
  cp $argv $argv.txt
end

function tcpd
  sudo tcpdump -A -s 9999 -i any "port $argv"
end

function b
  set encoded (echo -n $argv[1] | base64)
  echo $encoded
  echo $encoded | xclip
end

function bd
  set decoded (echo -n $argv[1] | base64 -d)
  echo $decoded
  echo $decoded | xclip
end

# Aliases

function ..
  cd ..
end

function ...
  cd ../..
end

function ....
  cd ../../..
end

function .....
  cd ../../../..
end

function ......
  cd ../../../../..
end

function ..2
  cd ../..
end

function ..3
  cd ../../..
end

function ..4
  cd ../../../..
end

function ..4
  cd ../../../..
end

function a
  ag --path-to-ignore ~/dotfiles/ansible/roles/terminal/files/grepignore $argv
end

function amend
  git commit --amend $argv
end

function colemaknordic
  setxkbmap colemaknordic -option altwin:swap_lalt_lwin -option caps:super; background-notify  "Switched to Colemak Nordic."
end

function colemak
  setxkbmap -layout us -variant colemak -option altwin:swap_lalt_lwin -option caps:super; background-notify "Switched to Colemak."
end

function toggle-colemak
  set current (setxkbmap -print -verbose 10 | grep layout)
  background-notify $current
  switch $current
  case '*colemaknordic'
    colemak
  case '*'
    colemaknordic
  end
end

function executable
  chmod +x $argv
end

function ga
  git add $argv
end

function gc
  git commit -m $argv
end

function gd
  git diff $argv
end

function gdd
  git diff --cached $argv
end

function gw
  git diff --word-diff $argv
end

function gl
  git lg $argv
end

function gp
  git pull --rebase $argv
end

function gs
  git status -s $argv
end

function k
  kubectl $argv
end

function kp
  kubectl get pods $argv
end

function ks
  kubectl get svc $argv
end

function kd
  kubectl get deploy $argv
end

function l
  ls -alF $argv
end

function la
  ls -A $argv
end

function less
  command less -S $argv
end

function lt
  ls -ltr $argv
end

function p
  pwd
end

function pa
  ps ax | a $argv
end

function r
  rg -S $argv
end

function sd
  trans sv:da $argv
end

function ds
  trans da:sv $argv
end

function ta
  tmux attach -t $argv
end

function tl
  tmux list-sessions
end

function td
  tmux detach
end

function ti
  tmux info
end

function tlc
  tmux list-commands
end

function tlk
  tmux list-keys
end

function trs
  tmux rename-session
end

function trw
  tmux rename-window
end

function ts
  tmux switch -t
end

function locate
  command locate -d ~/.config/mlocate/mlocate.db $argv
end

function init-base16
  source $HOME/.config/base16-shell/profile_helper.fish
end

function owning-package
  pacman -Qo $argv
end

function files-in-package
  pacman -Ql $argv
end

function dependencies-of
  pactree -r $argv
end

function who-depends-on
  pacman -Qi $argv | grep Required
end

function packages-without-dependencies
  pacman -Qdtq
end

function kl
  if test (count $argv) -gt 1
    set n $argv[1]
  else
    set n 0
  end
  kubectl get pods | grep $argv[1] | head -n 1 | awk '{print $1}' | xargs kubectl logs -f
end

function dunst_base16
  curl https://raw.githubusercontent.com/khamer/base16-dunst/master/themes/$argv.dunstrc >> $XDG_CONFIG_HOME/dunst/dunstrc
end

function take_screenshot
  mkdir -p ~/pictures/screenshots/
  scrot -e 'mv $f ~/pictures/screenshots/'
  notify-send "Took a screenshot!"
end

function generate_application_secret
  head -c 32 /dev/urandom | base64
end

function week
  date +%V
end

function translate
  pushd .
  cd ~/dev/translate
  pipenv run python translate $argv
  popd
end

function scalafmt-changed-files
  git status --short | awk '{print $2}' | xargs scalafmt
end

function k8s-images
  kubectl -n "$argv" get pods -o jsonpath='{.items[*].spec.containers[*].image}' | tr -s '[[:space:]]' '\n' | sort
end

function bluetooth-volume-up
  dbus-send --print-reply --system --dest=org.bluez /org/bluez/hci0/dev_38_18_4C_BC_F1_AA  org.bluez.MediaControl1.VolumeUp
end

function bluetooth-volume-down
  dbus-send --print-reply --system --dest=org.bluez /org/bluez/hci0/dev_38_18_4C_BC_F1_AA  org.bluez.MediaControl1.VolumeDown
end

function bluetooth-profile-a2dp
  pactl set-card-profile bluez_card.38_18_4C_BC_F1_AA a2dp_sink
end

# Not sure if this is hfp or hsp. It sounds very weird in any case.
# Why do I constantly hear myself?
# Can I only change volumes in alsamixer?
# ???
function bluetooth-profile-hfp
  pactl set-card-profile bluez_card.38_18_4C_BC_F1_AA headset_head_unit
end

function x
  xdg-open $argv
end

function posix-source
  for i in (cat $argv)
    set arr (echo $i | string match -r "([^=]+)=(.*)")
    set -gx $arr[2] $arr[3]
  end
end

function m4a-to-mp3
  ffmpeg -v 5 -y -i $argv -acodec libmp3lame -ac 2 -ab 64k output.mp3
end

function cl
  set newest_dir (ls -td ./* | head -1)
  cd $newest_dir
end

function hard-reset-jack
  rm -rf ~/.jackdrc ~/.config/jack/conf.xml ~/.config/rncbc.org/QjackCtl.conf
  kill -9 jackdbus
  sleep 1
  stomp_start_jack
end

function vimdiff
  nvim -d $argv
end

function weather --description "Show weather"
  curl -s "wttr.in/$argv" | head -n 17
end

function whereami
  http ifconfig.co/json
end

# Stolen from https://github.com/dmi3/fish
function search-contents --description "`ALT`+`CTRL`+`F` search (fuzzy) file by contents"
  ag --nobreak --no-numbers --noheading --max-count 100000 . 2>/dev/null \
      | fzf \
        -q "'" \
        --header 'Searching file contents' \
        --preview 'echo {} | cut -d ":" -f2 | bat --color=always --style=numbers --line-range=:500 ' \
      | string split ':' | head -n 1 | read -l result
  and commandline $result
  and commandline -f repaint
end

function path
  readlink -e $argv
end

function untar
  tar -xvzf $argv
end

function external-ip
  curl ifconfig.co
end

function bak
  cp $argv $argv.bak
end

function serve
  python -m http.server $argv
end

function pj
  python -m json.tool $argv
end

function printcert
  keytool -printcert -v -file $argv
end

function list-jks
  keytool -list -v -keystore $argv
end

function readram
  sudo dd if=/dev/mem | cat | strings
end

function cf
  vim ~/.config/fish
end

function ci3
  vim ~/.config/i3/config
end

function c
  cdh $argv
end

function youtube-mp3
  youtube-dl -t --extract-audio --audio-format mp3 $argv
end

function xc
  xclip -selection clipboard $argv
end

function nvim-debug
  nvim -V9vim-debug.log $argv
end

function get-recipe --description "Get recipe as markdown from an URL"
  if [ $argv[1] ]
        curl -sG "https://plainoldrecipe.com/recipe" -d "url=$argv[1]" |\
          pandoc -f html -t markdown-raw_html-native_divs-native_spans-fenced_divs-bracketed_spans
    else
        echo 'usage: get-recipe URL'
    end
end

function ws
  s wiki search
end

function w3m
  # Don't confirm quit
  command w3m -o confirm_qq=false $argv
end

function memorize
  mkdir -p /home/johan/.local/share/memorize_docker
  docker run --rm --name memorize -v /home/johan/.local/share/memorize_docker:/root/.local/share/memorize johan1a/memorize $argv
end
