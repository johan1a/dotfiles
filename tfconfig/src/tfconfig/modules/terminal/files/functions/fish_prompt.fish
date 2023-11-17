function fish_prompt --description 'Write out the prompt'
  set -l last_status $status

  if not set -q __fish_prompt_normal
    set -g __fish_prompt_normal (set_color normal)
  end

  # PWD
  set_color $fish_color_cwd
  echo -n (prompt_pwd)
  set_color normal

  printf ' '

  if [ (get_git_branch) ]
    printf '| %s ' (get_git_branch)
  end

  if [ (get_kube_context) ]
    printf '| %s ' (get_kube_context)
  end

  if not test $last_status -eq 0
  set_color $fish_color_error
  end

  echo -n ')'
  echo -n ' '

  set_color normal
end
