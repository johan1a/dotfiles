 " ____  _             _                  _
" |  _ \| |_   _  __ _(_)_ __    ___  ___| |_ _   _ _ __
" | |_) | | | | |/ _` | | '_ \  / __|/ _ \ __| | | | '_ \
" |  __/| | |_| | (_| | | | | | \__ \  __/ |_| |_| | |_) |
" |_|   |_|\__,_|\__, |_|_| |_| |___/\___|\__|\__,_| .__/
 "               |___/                             |_|

if &shell =~# 'fish$'
    set shell=bash
endif

set nocompatible              " Don't emulate vi

" The has('wsl') check does not work
function! IsWsl()
  let versionString = system("cat /proc/version")
  return versionString =~ "Microsoft"
endfunction

if empty(glob('~/.local/share/nvim/site/autoload/plug.vim'))
  silent !curl -fLo ~/.local/share/nvim/site/autoload/plug.vim --create-dirs
    \ https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim
  autocmd VimEnter * PlugInstall --sync | source $MYVIMRC
endif

call plug#begin()

if has('nvim')
  if !IsWsl()
    Plug 'Shougo/deoplete.nvim'
    Plug 'zchee/deoplete-jedi'
    Plug 'SirVer/ultisnips'
    Plug 'glacambre/firenvim', { 'do': { _ -> firenvim#install(0) } }
  endif

  Plug 'derekwyatt/vim-scala'
  Plug 'neoclide/coc.nvim', {'branch': 'release'} " branch release makes the start text disappear, irritating!
  Plug 'sbdchd/neoformat'
endif

if !IsWsl()
  Plug 'artur-shaik/vim-javacomplete2'
  Plug 'honza/vim-snippets'
  Plug 'jreybert/vimagit'
  Plug 'tpope/vim-dispatch'
  Plug 'tpope/vim-fireplace'
  Plug 'udalov/kotlin-vim'
  Plug 'venantius/vim-cljfmt'
endif

Plug 'airblade/vim-gitgutter'
Plug 'bkad/CamelCaseMotion'
Plug 'chriskempson/base16-vim'
Plug 'christoomey/vim-tmux-navigator'
Plug 'dag/vim-fish'
Plug 'dkprice/vim-easygrep'
Plug 'gurpreetatwal/vim-avro'
Plug 'ianks/vim-tsx'
Plug 'junegunn/fzf.vim'
Plug 'junegunn/vim-slash'
Plug 'justinmk/vim-sneak'
Plug 'leafgarland/typescript-vim'
Plug 'ludovicchabant/vim-gutentags'
Plug 'scrooloose/nerdtree'
Plug 'tommcdo/vim-lion'
Plug 'tpope/vim-commentary'
Plug 'tpope/vim-eunuch'
Plug 'tpope/vim-fugitive'
Plug 'tpope/vim-surround'
Plug 'vimwiki/vimwiki'

call plug#end()

let mapleader =  "\<Space>"

 " ____  _             _                          __ _
" |  _ \| |_   _  __ _(_)_ __     ___ ___  _ __  / _(_) __ _
" | |_) | | | | |/ _` | | '_ \   / __/ _ \| '_ \| |_| |/ _` |
" |  __/| | |_| | (_| | | | | | | (_| (_) | | | |  _| | (_| |
" |_|   |_|\__,_|\__, |_|_| |_|  \___\___/|_| |_|_| |_|\__, |
 "               |___/                                 |___/

" =========== ultisnips ===========

set rtp^=$HOME/.vim/bundle/ultisnips

" Trigger configuration. Do not use <tab> if you use https://github.com/Valloric/YouCompleteMe.
let g:UltiSnipsExpandTrigger="<c-e>"
let g:UltiSnipsJumpForwardTrigger="<c-e>"
let g:UltiSnipsJumpBackwardTrigger="<c-i>"
let g:UltiSnipsListSnippets="<c-p>"

inoremap <silent> <c-e> <C-R>=UltiSnips#ExpandSnippetOrJump()<cr>

let g:UltiSnipsEditSplit="vertical"
let g:UltiSnipsSnippetsDir="~/.vim/custom_snippets" " Custom snips dir
let g:UltiSnipsSnippetDirectories=["UltiSnips", "custom_snippets"]

" =========== gitgutter ===========

let g:gitgutter_max_signs = 1000

" =========== fzf ===========

set rtp+=~/.fzf
nnoremap <leader>n :FZF<cr>
nnoremap <leader>t :Tags<cr>
let $FZF_DEFAULT_COMMAND = 'ag -g ""'

" Customize fzf colors to match your color scheme
let g:fzf_colors =
\ { 'fg':      ['fg', 'Normal'],
  \ 'bg':      ['bg', 'Normal'],
  \ 'hl':      ['fg', 'Comment'],
  \ 'fg+':     ['fg', 'CursorLine', 'CursorColumn', 'Normal'],
  \ 'bg+':     ['bg', 'CursorLine', 'CursorColumn'],
  \ 'hl+':     ['fg', 'Statement'],
  \ 'info':    ['fg', 'PreProc'],
  \ 'border':  ['fg', 'Ignore'],
  \ 'prompt':  ['fg', 'Conditional'],
  \ 'pointer': ['fg', 'Exception'],
  \ 'marker':  ['fg', 'Keyword'],
  \ 'spinner': ['fg', 'Label'],
  \ 'header':  ['fg', 'Comment'] }

" =========== coc.nvim ===========


" Smaller updatetime for CursorHold & CursorHoldI
set updatetime=300

" don't give |ins-completion-menu| messages.
set shortmess+=c

" always show signcolumns
set signcolumn=yes

" Some server have issues with backup files, see #649
" set nobackup
" set nowritebackup

" Better display for messages
set cmdheight=2

" Use <c-space> for trigger completion.
inoremap <silent><expr> <c-y> coc#refresh()

" Use <cr> for confirm completion, `<C-g>u` means break undo chain at current position.
" Coc only does snippet and additional edit on confirm.
" inoremap <expr> <cr> pumvisible() ? "\<C-y>" : "\<C-g>u\<CR>"

" use <tab> for trigger completion and navigate to the next complete item
function! s:check_back_space() abort
  let col = col('.') - 1
  return !col || getline('.')[col - 1]  =~ '\s'
endfunction

inoremap <silent><expr> <Tab>
      \ pumvisible() ? "\<C-n>" :
      \ <SID>check_back_space() ? "\<Tab>" :
      \ coc#refresh()

" navigate diagnostics
nmap <silent> <F2> <Plug>(coc-diagnostic-prev)
nmap <silent> <leader>cy <Plug>(coc-diagnostic-next)

" Remap keys for gotos
nmap <silent> <leader>cc <Plug>(coc-definition)
nmap <silent> <leader>cn <Plug>(coc-type-definition)
nmap <silent> <leader>ci <Plug>(coc-implementation)
nmap <silent> <leader>cr <Plug>(coc-references)

" Remap for do codeAction of current line
nmap <leader>ac <Plug>(coc-codeaction)

" Remap for do action format
nnoremap <silent> F :call CocAction('format')<CR>

" Use K for show documentation in preview window
nnoremap <silent> K :call <SID>show_documentation()<CR>

function! s:show_documentation()
  if &filetype == 'vim'
    execute 'h '.expand('<cword>')
  else
    call CocAction('doHover')
  endif
endfunction

" Highlight symbol under cursor on CursorHold
autocmd CursorHold * silent call CocActionAsync('highlight')

" Remap for rename current word
nmap <leader>cx <Plug>(coc-rename)

" Show all diagnostics
nnoremap <silent> <space>ca  :<C-u>CocList diagnostics<cr>
" Find symbol of current document
nnoremap <silent> <space>co  :<C-u>CocList outline<cr>
" Search workspace symbols
nnoremap <silent> <space>cs  :<C-u>CocList -I symbols<cr>
" Do default action for next item.
nnoremap <silent> <space>cj  :<C-u>CocNext<CR>
" Do default action for previous item.
nnoremap <silent> <space>ck  :<C-u>CocPrev<CR>
" Resume latest coc list
nnoremap <silent> <space>cp  :<C-u>CocListResume<CR>

" Notify coc.nvim that <enter> has been pressed.
" Currently used for the formatOnType feature.
inoremap <silent><expr> <cr> pumvisible() ? coc#_select_confirm()
      \: "\<C-g>u\<CR>\<c-r>=coc#on_enter()\<CR>"

let g:coc_global_extensions = ['coc-tslint-plugin', 'coc-tsserver', 'coc-emmet', 'coc-css', 'coc-html', 'coc-json', 'coc-yank', 'coc-prettier']

" =========== Vimwiki ===========

let g:vimwiki_list = [{'path': '~/vimwiki/',
                     \ 'syntax': 'markdown', 'ext': '.md'}]

" =========== EasyGrep ===========

let g:EasyGrepFilesToExclude='*.log,tags,.git,external_documentation,documentation,build,classes'
let g:EasyGrepRecursive=1
let g:EasyGrepCommand=1

" =========== Gutentags =========

:set statusline+=%{gutentags#statusline('[Generating...]')}

" =========== vim-dispatch ===========

augroup vim_dispatch
    autocmd!
    autocmd FileType java       let b:dispatch = 'javac %'
    autocmd FileType groovy     let b:dispatch = './gradlew build'
    autocmd FileType typescript let b:dispatch = 'ng test && ng lint'
    autocmd FileType python     let b:dispatch = 'python -m unittest discover'
augroup END

nnoremap <leader>d :Dispatch<CR>

" =========== tmux-navigator ===========

let g:tmux_navigator_disable_when_zoomed = 1 " Disable tmux auto zoom out

" =========== vim-fireplace ===========

nnoremap <leader>w :Eval<CR>
vnoremap <leader>w :Eval<CR>
nnoremap <a-r> :Require<CR>

" =========== CamelCaseMotion ===========

map <silent> w <Plug>CamelCaseMotion_w
map <silent> b <Plug>CamelCaseMotion_b
map <silent> e <Plug>CamelCaseMotion_e
map <silent> ge <Plug>CamelCaseMotion_ge
sunmap w
sunmap b
sunmap e
sunmap ge

" =========== unimpaired-vim ===========

nmap <c-up>   [e
" nmap <c-d> ]e
vmap <c-up>   [egv
" vmap <c-d> ]egv

" =========== vim-sneak ===========

let g:sneak#label = 1

" =========== Magit ===========

" nnoremap <leader>ma :Magit<cr>

  " ____                           _            _   _   _
 " / ___| ___ _ __   ___ _ __ __ _| |  ___  ___| |_| |_(_)_ __   __ _ ___
" | |  _ / _ \ '_ \ / _ \ '__/ _` | | / __|/ _ \ __| __| | '_ \ / _` / __|
" | |_| |  __/ | | |  __/ | | (_| | | \__ \  __/ |_| |_| | | | | (_| \__ \
 " \____|\___|_| |_|\___|_|  \__,_|_| |___/\___|\__|\__|_|_| |_|\__, |___/
  "                                                             |___/

syntax enable
set autoindent
set smartindent
set autoread    " Automatically read external changes
set scrolloff=1 " Leave margin when scrolling
set sidescrolloff=5
set display+=lastline
set hidden
set gdefault
" set fileformat=unix

" Magic to make autoread actually work
" Trigger autoread when changing buffers while inside vim:
augroup general_settings
    autocmd!
    au FocusGained,BufEnter * :checktime
    au CursorHold * checktime
augroup END

if filereadable(expand('~/.vimrc_background'))
  let base16colorspace=256
  source ~/.vimrc_background
endif

" =========== help ===========

augroup helpfiles
  au!
  au BufRead,BufEnter */doc/* wincmd L
augroup END

" =========== filetypes ===========

au BufRead,BufNewFile *.sbt set filetype=scala

" jsoc comment syntax highlighting support
autocmd FileType json syntax match Comment +\/\/.\+$+

augroup filetypes
  autocmd BufNewFile,BufRead *.gson      setlocal ft=groovy
  autocmd BufNewFile,BufRead *.ts        setlocal ft=typescript
  autocmd Filetype groovy                setlocal makeprg=./gradlew\ test
  autocmd BufRead,BufNewFile Vagrantfile set filetype=ruby
  autocmd BufRead,BufNewFile Jenkinsfile set filetype=groovy
  autocmd FileType json                  nnoremap <buffer> <leader>f :%!python -m json.tool<cr>
  autocmd FileType xml                   nnoremap <buffer> <leader>f :%!xmllint --format -<cr>
  autocmd FileType scala                 nnoremap <buffer> <leader>f :Neoformat<cr>
  autocmd FileType typescript            nnoremap <buffer> <leader>f :Neoformat<cr>
  autocmd FileType typescript            setlocal tabstop=4 shiftwidth=4
  autocmd FileType python                nnoremap <buffer> <leader>f :Neoformat<cr>
  autocmd FileType clojure               nnoremap <buffer> <leader>f :!lein cljfmt fix %<cr>
  autocmd FileType fish compiler fish
  " Set this to have long lines wrap inside comments.
  autocmd FileType fish                  setlocal textwidth=79
  autocmd Filetype groovy                setlocal tabstop=4 shiftwidth=4
  autocmd FileType haskell               setlocal tabstop=4
  autocmd Filetype yaml                  setlocal cursorcolumn
  autocmd Filetype ts                    setlocal filetype=typescript
  autocmd Filetype tsx                   setlocal filetype=typescript.tsx
  autocmd BufRead,BufNewFile *.avdl      setlocal filetype=avdl
augroup END


" =========== search ===========

set grepprg=ag\ $*

nnoremap <leader>g :Grep 

" Fuzzy finding
" :b <substring of open buffer> to jump to buffer
" :find <substring of open buffer>
set path+=**

" Tab completion behavior
set wildmode=longest,list,full

" Display all matches when tab completing
set wildmenu

" =========== Splits ===========
augroup splits
    autocmd!
    autocmd VimResized * wincmd =
augroup END

set splitbelow
set splitright

" =========== Tabs and spaces  ===========
set expandtab                          " insert space characters whenever the tab key is pressed,
set tabstop=2                          " the number of space characters that will be inserted when the tab key is pressed
set shiftwidth=2                       " the number of space characters inserted for indentation with the reindent operations (<< and >>)
set softtabstop=0
set list                               " Display tabs and trailing spaces
set listchars=tab:▸–,trail:·
set listchars+=nbsp:⦸                  " CIRCLED REVERSE SOLIDUS (U+29B8, UTF-8: E2 A6 B8)
set nojoinspaces                       " don't autoinsert two spaces after '.', '?', '!' for join command

" Set color of listchars
highlight ExtraWhitespace ctermbg=red guibg=red
match ExtraWhitespace /\s\+$/

" =========== Numbering  ===========

set number
set relativenumber
set ruler
set showcmd

 " _  __
" | |/ /___ _   _   _ __ ___   __ _ _ __  ___
" | ' // _ \ | | | | '_ ` _ \ / _` | '_ \/ __|
" | . \  __/ |_| | | | | | | | (_| | |_) \__ \
" |_|\_\___|\__, | |_| |_| |_|\__,_| .__/|___/
 "          |___/                  |_|

" Copy buffer
nnoremap <leader>x :!xclip -selection clipboard %<cr>

" Jump to tag under cursor
nnoremap <leader>i <C-]>zz

" Jump back up the tag stack
nnoremap <leader>u <C-t>zz
nnoremap <c-p> :lprev<cr>
nnoremap <c-n> :lnext<cr>

" Go to alternate file
nnoremap <leader><leader> <C-^>

nnoremap <C-W> :w<CR>

nnoremap <leader>r :source ~/.vimrc<CR>
nnoremap <leader>k :NERDTreeFind<CR>
nnoremap <leader>q :NERDTreeClose<CR>:q<CR>
nnoremap <leader>bd :bdelete<CR>

" Increment / Decrement numbers
nnoremap <A-a> <C-a>
nnoremap <A-x> <C-x>

" replace and jump
nnoremap c* *Ncgn
nnoremap gs :%s//<Left>
vnoremap gs :s/\%V/<Left>

" Git
nnoremap <leader>0 :Gblame<CR>

" Live preview of :%s/
if has('nvim')
  set inccommand=split
endif

" Copy / Paste

" Yank from cursor to end of line
map Y y$

" Search using copy buffer
nnoremap <leader>7 /<C-R>0<CR>
nnoremap <leader>' ?<C-R>0<CR>

" ALWAYS use the clipboard for ALL operations (as opposed
" to interacting with the '+' and/or '*' registers explicitly):
set clipboard+=unnamedplus

" Use system copy buffer
nnoremap y "*y
vnoremap y "*y
nnoremap p "*p
vnoremap p "*p
vnoremap c "*c
nnoremap c "*c
vnoremap C "*C
nnoremap C "*C
nnoremap d "*d
vnoremap d "*d
nnoremap <leader>cp           olog.info("<esc>"*pa: ${<esc>"*pa}")<esc>

" noremap <d>   <c-w>+
" noremap <up>     <c-w>-
noremap <left>  5<c-w><
noremap <right> 5<c-w>>

" neovim term
tnoremap <C-o> <C-\><C-n>
tnoremap <leader><esc> <C-\><esc>

" Execute a shell command and read in the results
noremap Q yyp!!$SHELL<CR>
nnoremap <leader>Qr :!./%<CR>

" Tabs and splits

nnoremap <s-l> :bnext<cr>
nnoremap <s-h> :bprevious<cr>
nnoremap <leader>bl :ls<cr>
nnoremap <leader>bs :sp<cr>
nnoremap <leader>bt :vs<cr>
nnoremap <tab> :tabn<cr>
" the tab mapping avove ruins <c-i>, why?
nnoremap <c-i> <c-i>
nnoremap <s-tab> :tabp<cr>

" Holy shell, Batman!!!
function! Fish_open()
  set shell=fish
  :tabe fish
  term
  set shell=bash
endfunction

nnoremap <leader>j :call Fish_open()<cr>a

" Change to the directory of the current file
nnoremap <silent> cd :<c-u>cd %:h \| pwd<cr>

nnoremap <leader>aa :e ~/.vimrc<cr>
nnoremap <leader>ai :e ~/.config/i3/config<cr>

nnoremap <leader>mr :Rename 
nnoremap <leader>mm :Move 
nnoremap <leader>ms :SudoWrite<cr>

nnoremap <leader>1 :!

 "Decrease number
nnoremap <C-c> <C-x>

set cedit=<ESC>

if filereadable(glob("~/.vimrc.local"))
    source ~/.vimrc.local
endif