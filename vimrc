syntax enable
filetype plugin indent on
set grepprg=grep\ -nH\ $*
            
set number

" insert space characters whenever the tab key is pressed,
set noexpandtab


" The number of space characters that will be inserted when the tab key is pressed
set tabstop=4
" The number of space characters inserted for indentation
set shiftwidth=4
set softtabstop=0

set relativenumber

noremap <Up> <nop>
noremap <Down> <nop>
noremap <Left> <nop>
noremap <Right> <nop>

execute pathogen#infect()
