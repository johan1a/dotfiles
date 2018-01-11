
set fish_greeting

if status --is-interactive;
    [ -e $HOME/.aliases.sh ]; and . $HOME/.aliases.sh
end

source ~/.config/fish/functions/git.fish

alias sf="source ~/.config/fish/config.fish"
