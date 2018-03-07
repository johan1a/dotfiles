
function get_git_branch
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'
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

# Mysterious black magic spell.
# Some say these ancient words have the power to fix
# even the worst cases of Dell latitude CPU throttling.
# But who knows at what cost? Use at your own risk...
function black-magic-spell
    sudo modprobe msr
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

function colemak
    echo "Switched to Colemak."
    setxkbmap us -variant colemak
    fixaltgr
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
    pip2 list | awk  ' {print $1}' | xargs sudo pip2 install -U
    pip3 list | awk  ' {print $1}' | xargs sudo pip3 install -U
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
