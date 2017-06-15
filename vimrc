" Don't emulate vi
set nocompatible
filetype off                  " required by Vundle

" set the runtime path to include Vundle and initialize
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

Plugin 'tpope/vim-fugitive'

Plugin 'chriskempson/base16-vim'

Plugin 'git://github.com/godlygeek/tabular.git'

Plugin 'https://github.com/ctrlpvim/ctrlp.vim.git'

" All of your Plugins must be added before the following line
call vundle#end()            " required
"  Brief help
" :PluginList       - lists configuredlugins
" :PluginInstall    - installslugins; append `!` to update or just :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unusedlugins; append `!` to auto-approve removal

" see :h vundle for more details or wiki for FAQ
" Put your non-Plugin stuff after this line


" =========== General settings ===========
syntax enable
filetype plugin indent on


" Automatically read external changes
set autoread


if filereadable(expand("~/.vimrc_background"))
  let base16colorspace=256
  source ~/.vimrc_background
endif


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

" =========== search ===========

set grepprg=grep\ -nH\ $*

" Fuzzy finding
" :b <substring of open buffer> to jump to buffer
" :find <substring of open buffer> 
set path+=**

" Display all matches when tab completing
set wildmenu

" Leave margin when scrolling
set scrolloff=1
set sidescrolloff=5

set display+=lastline

" =========== ctags ===========
" Make tags. Requires ctags
command! MakeTags !ctags -R .

" =========== Splits ===========
:autocmd VimResized * wincmd =

" =========== Tabs and spaces  ===========
" insert space characters whenever the tab key is pressed,
set expandtab

" The number of space characters that will be inserted when the tab key is pressed
set tabstop=2

" The number of space characters inserted for indentation with the reindent
" operations (<< and >>)
set shiftwidth=2
set softtabstop=0

" Display tabs and trailing spaces
set list
set listchars=tab:▸–,trail:·

" =========== Numbering  ===========
set number

set relativenumber
set ruler

set showcmd

" =========== Key maps  ===========

" Remap leader
let mapleader =  "\<Space>"

nnoremap <leader>f :find<space>

" ctrl+]        Jump to tag under cursor
nnoremap <leader>j <C-]>
" g+ctrl+]      List ambiguous tags
nnoremap <leader>gj <C-]>
" ctrl+t        Jump back up the tag stack
nnoremap <leader>u <C-]>

" ctrl+x is bound to tmux TODO
" ctr+x ctrl+n  just this file
" ctr+x ctrl+f  filenames
" ctr+x ctrl+]  tags only
" ctrl+n        autocomplete

" gf            open file under cursor

" "+y           copy to system clipboard

nnoremap <leader>m :MakeTags<CR>

" Reload config
nnoremap <leader>r :source ~/.vimrc<CR>

nnoremap <A-j>  <C-W>j
nnoremap <A-k>  <C-W>k
nnoremap <A-h>  <C-W>h
nnoremap <A-l>  <C-W>l

nnoremap Y y$

" Disable arrow keys 
noremap <Up> <nop>
noremap <Down> <nop>
noremap <Left> <nop>
noremap <Right> <nop>

inoremap § <esc>
vnoremap § <esc>
noremap § <esc>
nnoremap § <esc>





