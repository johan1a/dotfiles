function get-base16-themes
  basename -a $XDG_CONFIG_HOME/base16-shell/scripts/* | string replace -r '^base16-' '' | string replace -r '.sh$' ''
end

complete --command base16 --erase
complete --command base16 --no-files -a "(get-base16-themes)"
