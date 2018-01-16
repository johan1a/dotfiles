
function get_git_branch
    git branch 2> /dev/null | sed -e '/^[^*]/d' -e 's/* \(.*\)/\1/'
end

function mc
    mkdir -p $argv; and cd $argv
end

function ml
    cd $argv; and ll
end

function backup
    cp "$argv"{,.bak};
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

function ci3
    cd ~/.config/i3
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

