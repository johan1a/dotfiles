
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

function weathercheck
    curl "wttr.in/$argv"
end
