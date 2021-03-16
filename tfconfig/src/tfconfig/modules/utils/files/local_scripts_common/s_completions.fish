
function get-help-text
  if test -f "$argv".help
    cat "$argv".help
  else
    echo ""
  end
end

complete --command s --erase


set default_root "$HOME/dotfiles/tfconfig/src/tfconfig/modules/utils/files/local_scripts"
set -q local_scripts_root || set local_scripts_root $default_root
set base_dir $local_scripts_root

set -l all_commands (ls $base_dir)
for command in $all_commands
  if not string match '*.help' $command > /dev/null
    set -l help (get-help-text "$base_dir/$command")
    complete --no-files --command s --condition "not __fish_seen_subcommand_from $all_commands" -a $command -d "$help"
  end
end

for dir in $all_commands
  if test -d $base_dir/$dir
    set -l subdirs (ls $base_dir/$dir)
    for subdir in $subdirs
      set -l cmd "$base_dir/$dir/$subdir"
      if not string match '*.help' $cmd > /dev/null
        set -l help (get-help-text "$cmd")
        complete -f --command s -n "__fish_seen_subcommand_from $dir; and not __fish_seen_subcommand_from $subdir" -a "$subdir" -d "$help"
      end
    end
  end
end
