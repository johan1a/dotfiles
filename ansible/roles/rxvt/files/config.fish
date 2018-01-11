
set fish_greeting

if status --is-interactive;
    [ -e $HOME/.aliases.sh ]; and . $HOME/.aliases.sh
end

alias sf="source ~/.config/fish/config.fish"
