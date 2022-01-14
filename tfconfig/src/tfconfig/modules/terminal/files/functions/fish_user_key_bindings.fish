function fish_user_key_bindings
  fish_vi_key_bindings
  fzf_key_bindings
  bind -M visual V edit_command_buffer
  bind -M insert \cs search-contents
  bind -M insert \cn "s wiki search"
  bind -M insert \ce "$VISUAL (fzf --preview 'bat --color=always --style=numbers --line-range=:500 {}')"
end

