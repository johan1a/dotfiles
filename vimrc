colo evening
syntax enable
filetype plugin indent on

set grepprg=grep\ -nH\ $*
            
set number

" insert space characters whenever the tab key is pressed,
set expandtab


" The number of space characters that will be inserted when the tab key is pressed
set tabstop=2
" The number of space characters inserted for indentation with the reindent
" operations (<< and >>)
set shiftwidth=2
set softtabstop=0

set relativenumber

noremap <Up> <nop>
noremap <Down> <nop>
noremap <Left> <nop>
noremap <Right> <nop>

execute pathogen#infect()
