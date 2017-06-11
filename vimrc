" =========== General settings ===========
colo default 
syntax enable
filetype plugin indent on

" Don't emulate vi
set nocompatible

" =========== netrw ===========
" Enable netrw 'plugins'
filetype plugin on

" netrw tree view
let g:netrw_liststyle = 3

" Don't display info on the top of window
let g:netrw_banner = 0

" " sort is affecting only: directories on the top, files below
" let g:netrw_sort_sequence = '[\/]$,*'

" use the previous window to open file
let g:netrw_browse_split = 4


set grepprg=grep\ -nH\ $*

" Fuzzy finding
" :b <substring of open buffer> to jump to buffer
set path+=**

" Display all matches when tab completing
set wildmenu

" =========== ctags ===========
" Make tags. Requires ctags
command! MakeTags !ctags -R .

            
" =========== Tabs and spaces  ===========
" insert space characters whenever the tab key is pressed,
set expandtab

" The number of space characters that will be inserted when the tab key is pressed
set tabstop=2

" The number of space characters inserted for indentation with the reindent
" operations (<< and >>)
set shiftwidth=2
set softtabstop=0

" =========== Numbering  ===========
set number

set relativenumber

" =========== Key maps  ===========

" ctrl+]        Jump to tag under cursor
" g+ctrl+]      List ambiguous tags
" ctrl+t        Jump back up the tag stack

" ctrl+x is bound to tmux TODO
" ctr+x ctrl+n  just this file
" ctr+x ctrl+f  filenames
" ctr+x ctrl+]  tags only
" ctrl+n        autocomplete

" gf            open file under cursor

" "+y           copy to system clipboard

" Disable arrow keys 
noremap <Up> <nop>
noremap <Down> <nop>
noremap <Left> <nop>
noremap <Right> <nop>

execute pathogen#infect()



