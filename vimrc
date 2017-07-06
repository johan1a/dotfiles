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

Plugin 'https://github.com/Shougo/deoplete.nvim.git'
Plugin 'https://github.com/clojure-vim/async-clj-omni.git'
Plugin 'https://github.com/zchee/deoplete-jedi.git'
Plugin 'artur-shaik/vim-javacomplete2'

" All of your Plugins must be added before the following line
call vundle#end()            " required
"  Brief help
" :PluginList       - lists configuredlugins
" :PluginInstall    - installslugins; append `!` to update or just :PluginUpdate
" :PluginSearch foo - searches for foo; append `!` to refresh local cache
" :PluginClean      - confirms removal of unusedlugins; append `!` to auto-approve removal

" see :h vundle for more details or wiki for FAQ
" Put your non-Plugin stuff after this line



" =========== deoplete ===========
" if :echo has("python3") returns 1, then you're done;
" Otherwise enable Python3 interface with pip:
" pip3 install --upgrade neovim
" :UpdateRemotePlugins and restart neovim

let g:deoplete#enable_at_startup = 1
if !exists('g:deoplete#omni#input_patterns')
  let g:deoplete#omni#input_patterns = {}
endif
let g:deoplete#disable_auto_complete = 1

" Enter: complete&close popup if visible (so next Enter works); else: break undo
inoremap <silent><expr> <Cr> pumvisible() ?
            \ deoplete#mappings#close_popup() : "<C-g>u<Cr>"

" deoplete tab-complete
inoremap <expr><tab> pumvisible() ? "\<c-n>" : "\<tab>"

" Ctrl-Space: summon FULL (synced) autocompletion
inoremap <silent><expr> <C-Space> deoplete#mappings#manual_complete()

" Escape: exit autocompletion, go to Normal mode
inoremap <silent><expr> <Esc> pumvisible() ? "<C-e><Esc>" : "<Esc>"

autocmd InsertLeave,CompleteDone * if pumvisible() == 0 | pclose | endif

let g:deoplete#keyword_patterns = {}
let g:deoplete#keyword_patterns.clojure = '[\w!$%&*+/:<=>?@\^_~\-\.#]*'

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

let g:netrw_altv = 1
let g:netrw_winsize = 25

" =========== search ===========

set grepprg=grep\ -nH\ $*

" Fuzzy finding
" :b <substring of open buffer> to jump to buffer
" :find <substring of open buffer> 
set path+=**

" Tab completion behavior
set wildmode=longest,list,full
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
"
nnoremap <leader><leader> <C-^>

nnoremap <leader>m :MakeTags<CR>

" Reset search
nnoremap <leader>n /ö666ö<CR>

" Reload config
nnoremap <leader>r :source ~/.vimrc<CR>

nnoremap <leader>h <C-W>H
nnoremap <leader>j <C-W>J
nnoremap <leader>k <C-W>K
nnoremap <leader>l <C-W>L
nnoremap <leader>o <C-W>r

nnoremap <leader>e :Lexplore<CR>

" ctrl+p
" <C-W><C-v> open in vertical split
" <C-W><C-x> open in horizontal split

" <C-o> older jump position
" <C-i> newer jump position

" Split navigation
nnoremap <A-j>  <C-W>j
nnoremap <A-k>  <C-W>k
nnoremap <A-h>  <C-W>h
nnoremap <A-l>  <C-W>l

nnoremap ö {
nnoremap ä }

" replace and jump
nnoremap c* *Ncgn


" Yank from cursor to end of line
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





