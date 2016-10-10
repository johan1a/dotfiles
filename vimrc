syntax enable
filetype plugin indent on
set grepprg=grep\ -nH\ $*
            
" automatically inserts one extra level of indentation in some cases, and
" works for C-like files.
" set smartindent
" is more customizable, but also more strict when it comes to syntax.
" set cindent
set number

" set copyindent
" set preserveindent

" insert space characters whenever the tab key is pressed,
set expandtab

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
