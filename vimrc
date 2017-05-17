colo default 
syntax enable
filetype plugin indent on


" netrw tree view
let g:netrw_liststyle = 3

" Don't display info on the top of window
let g:netrw_banner = 0

" " sort is affecting only: directories on the top, files below
" let g:netrw_sort_sequence = '[\/]$,*'

" use the previous window to open file
let g:netrw_browse_split = 4

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
