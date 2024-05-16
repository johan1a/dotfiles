lua << EOF
--  ____  _             _                  _
-- |  _ \| |_   _  __ _(_)_ __    ___  ___| |_ _   _ _ __
-- | |_) | | | | |/ _` | | '_ \  / __|/ _ \ __| | | | '_ \
-- |  __/| | |_| | (_| | | | | | \__ \  __/ |_| |_| | |_) |
-- |_|   |_|\__,_|\__, |_|_| |_| |___/\___|\__|\__,_| .__/
--                |___/                             |_|


vim.g.python3_host_prog = '/usr/bin/python3'

-- Install vim-plug if not installed
if vim.fn.glob('~/.local/share/nvim/site/autoload/plug.vim') == '' then
  vim.cmd('silent !curl -fLo ~/.local/share/nvim/site/autoload/plug.vim --create-dirs https://raw.githubusercontent.com/junegunn/vim-plug/master/plug.vim')
  vim.cmd('autocmd VimEnter * PlugInstall --sync | source $MYVIMRC')
end
local Plug = vim.fn['plug#']

vim.call('plug#begin')

Plug 'Shougo/deoplete.nvim'
Plug 'zchee/deoplete-jedi'
Plug 'SirVer/ultisnips'
Plug 'sbdchd/neoformat'

Plug('glacambre/firenvim', { ['do']=  function()
  vim.cmd("call firenvim#install(0)")
end })
Plug('prettier/vim-prettier', { ['do']='yarn install', ['for']={'javascript', 'typescript', 'css', 'less', 'scss', 'json', 'graphql', 'markdown', 'vue', 'svelte', 'yaml', 'html'}})
Plug('scalameta/nvim-metals', { branch='main' })
Plug 'neovim/nvim-lspconfig'
Plug 'nvim-lua/plenary.nvim'

-- Only run auto omnicomplete for languages where we are likely of having a
-- language server running to avoid annoying error messages.
Plug('BrandonRoehl/auto-omni', { ['for']='scala'}) -- Trigger automatic omnicompletion
Plug('pocco81/auto-save.nvim', { branch='main' })
Plug 'artur-shaik/vim-javacomplete2'
Plug 'honza/vim-snippets'
Plug 'tpope/vim-fireplace'
Plug 'udalov/kotlin-vim'
Plug 'airblade/vim-gitgutter'
Plug 'bkad/CamelCaseMotion'
Plug 'chriskempson/base16-vim'
Plug 'christoomey/vim-tmux-navigator'
Plug 'dag/vim-fish'
Plug 'gurpreetatwal/vim-avro'
Plug 'ianks/vim-tsx'
Plug('junegunn/fzf', { ['do'] = function()
    vim.cmd("call fzf#install()")
end })
Plug 'junegunn/fzf.vim'
Plug 'junegunn/vim-slash'
Plug 'justinmk/vim-sneak'
Plug 'leafgarland/typescript-vim'
Plug 'ludovicchabant/vim-gutentags'
Plug 'scrooloose/nerdtree'
Plug 'tommcdo/vim-lion'
Plug 'tpope/vim-commentary'
Plug 'tpope/vim-fugitive'
Plug 'tpope/vim-surround'
Plug 'vimwiki/vimwiki'

vim.call('plug#end')

vim.g.mapleader = " "

if vim.fn.filereadable(vim.fn.expand('~/.vimrc_background')) == 1 then
    vim.g.base16colorspace = 256
    vim.cmd('source ~/.vimrc_background')
end

--  ____  _             _                          __ _
-- |  _ \| |_   _  __ _(_)_ __     ___ ___  _ __  / _(_) __ _
-- | |_) | | | | |/ _` | | '_ \   / __/ _ \| '_ \| |_| |/ _` |
-- |  __/| | |_| | (_| | | | | | | (_| (_) | | | |  _| | (_| |
-- |_|   |_|\__,_|\__, |_|_| |_|  \___\___/|_| |_|_| |_|\__, |
--                |___/                                 |___/

-- =========== autosave ===========

require("auto-save").setup {
        enabled = true,
        execution_message = {
          message = function()
            return ("AutoSave: saved at " .. vim.fn.strftime("%H:%M:%S"))
          end,
          dim = 0.18,
          cleaning_interval = 1250, -- (milliseconds) automatically clean MsgArea after displaying `message`. See :h MsgArea
        },
        trigger_events = {"InsertLeave", "TextChanged"},
        condition = function(buf)
          local fn = vim.fn
          local utils = require("auto-save.utils.data")

          if
            fn.getbufvar(buf, "&modifiable") ~= 1 or
            utils.not_in(fn.getbufvar(buf, "&filetype"), {"scala"}) or
            vim.fn.expand("%:e") == "sbt"  then
            return false -- can't save
          end
          return true -- can save
        end,
        write_all_buffers = false,
        debounce_delay = 235,
}

if vim.g.started_by_firenvim then
    local fc = vim.g.firenvim_config['localSettings']
    fc['https://www.messenger.com'] = { takeover = 'never', priority = 1 }
    fc['https://discord.com'] = { takeover = 'never', priority = 1 }
end

-- =========== auto-omni ===========
-- Keys that trigger omnicomplete.
-- I added '.' to the default.
vim.g.auto_omnicomplete_key = 'period . a b c d e f g h i j k l m n o p q r s t u v w x y z ' ..
                                'A B C D E F G H I J K L M N O P Q R S T U V W X Y Z'

-- =========== nvim-metals ===========

-- Remove F from shortmess. set shortmess-=F NOTE: Without doing this, autocommands that deal with filetypes prohibit messages from being shown... and since we heavily rely on this, this must be set.
vim.opt.shortmess:remove("F")

--" NOTE: It's higly recommened to set your `statusBarProvider` to `on`. This
--" enables `metals/status` and also other helpful messages that are shown to you.
--" However, to enable this you _must_ have the metals status shown in your status
--" bar somehow. This can be by using the provided |metals#status()| function, or
--" by just accessing it with Lua via `vim.g['metals_status']`. Everything will
--" still work without this, but the status messages and feedback that you will
--" Enable nvim-metals statusBarProvider
--" This doesn't work when indented
metals_config = require('metals').bare_config()
metals_config.init_options.statusBarProvider = 'on'


-- Language servers
require'lspconfig'.clojure_lsp.setup{}
-- Install with:
-- sudo npm install -g @vue/language-server
require'lspconfig'.volar.setup {
  filetypes = {'typescript', 'javascript', 'javascriptreact', 'typescriptreact', 'vue', 'json'}
}

vim.cmd([[
  augroup lsp
    autocmd!
    autocmd FileType scala,sbt lua require('metals').initialize_or_attach(metals_config)
  augroup end
]])
vim.o.statusline = vim.g.metals_status or ''

-- =========== ultisnips ===========
-- After editing, reload changes with:
-- :call UltiSnips#RefreshSnippets()

if not string.find(vim.o.runtimepath, '~/.vim/bundle/ultisnips') then
  vim.o.runtimepath = vim.o.runtimepath .. ',~/.vim/bundle/ultisnips'
end

-- Trigger configuration. Do not use <tab> if you use https://github.com/Valloric/YouCompleteMe.
vim.g.UltiSnipsExpandTrigger = "<c-e>"
vim.g.UltiSnipsJumpForwardTrigger = "<c-e>"
vim.g.UltiSnipsJumpBackwardTrigger = "<c-i>"
vim.g.UltiSnipsListSnippets = "<c-p>"

vim.api.nvim_set_keymap('i', '<silent> <c-e> <c-R>', '<cmd>lua UltiSnips#ExpandSnippetOrJump()<cr>', {silent = true})

vim.g.UltiSnipsEditSplit = "vertical"
vim.g.UltiSnipsSnippetsDir = "~/.vim/custom_snippets"
vim.g.UltiSnipsSnippetDirectories = {"UltiSnips", "custom_snippets"}

-- =========== gitgutter ===========
vim.g.gitgutter_max_signs = 1000

-- =========== fzf ===========

vim.cmd('set rtp+=~/.fzf')

vim.api.nvim_set_keymap('n', '<leader>n', ':Files!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>g', ':Rg!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>tb', ':Buffers!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>o', ':Buffers!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>tl', ':Lines!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>th', ':History!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>tc', ':Commits!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>to', ':Commands!<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>te', ':Helptags!<CR>', { noremap = true })

vim.env.FZF_DEFAULT_COMMAND = 'ag -g ""'

-- Customize FZF colors to match your color scheme
vim.g.fzf_colors = {
  fg = {'fg', 'Normal'},
  bg = {'bg', 'Normal'},
  hl = {'fg', 'Comment'},
  ['fg+'] = {'fg', 'CursorLine', 'CursorColumn', 'Normal'},
  ['bg+'] = {'bg', 'CursorLine', 'CursorColumn'},
  ['hl+'] = {'fg', 'Statement'},
  info = {'fg', 'PreProc'},
  border = {'fg', 'Ignore'},
  prompt = {'fg', 'Conditional'},
  pointer = {'fg', 'Exception'},
  marker = {'fg', 'Keyword'},
  spinner = {'fg', 'Label'},
  header = {'fg', 'Comment'}
}

-- Set smaller updatetime for CursorHold & CursorHoldI
vim.o.updatetime = 300

-- Don't give ins-completion-menu messages
vim.o.shortmess = vim.o.shortmess .. 'c'

-- Better display for messages
vim.o.cmdheight = 2



vim.g.vimwiki_list = {{
    path = '~/vimwiki/',
    syntax = 'markdown',
    ext = '.md'
}}



-- =========== EasyGrep ===========
vim.g.EasyGrepFilesToExclude = '*.log,tags,.git,external_documentation,documentation,build,classes'
vim.g.EasyGrepRecursive = 1
vim.g.EasyGrepCommand = 1

vim.o.statusline = vim.o.statusline .. '%{gutentags#statusline("[Generating...]")}'

if vim.fn.executable('rg') then
    vim.g.gutentags_file_list_command = 'rg --files'
end

-- =========== tmux-navigator ===========
vim.g.tmux_navigator_disable_when_zoomed = 1


-- =========== vim-fireplace ===========

vim.api.nvim_set_keymap('n', '<leader>e', ':Eval<CR>', { noremap = true })
vim.api.nvim_set_keymap('n', '<leader>y', ':Require<CR>', { noremap = true })
vim.api.nvim_set_keymap('v', '<leader>e', ':Eval<CR>', { noremap = true })

-- =========== CamelCaseMotion ===========

vim.api.nvim_set_keymap('n', '<silent> w', '<Plug>CamelCaseMotion_w', { silent = true })
vim.api.nvim_set_keymap('n', '<silent> b', '<Plug>CamelCaseMotion_b', { silent = true })
vim.api.nvim_set_keymap('n', '<silent> e', '<Plug>CamelCaseMotion_e', { silent = true })
vim.api.nvim_set_keymap('n', '<silent> ge', '<Plug>CamelCaseMotion_ge', { silent = true })

-- =========== vim-sneak ===========

vim.g['sneak#label'] = 1

--   ____                           _            _   _   _
--  / ___| ___ _ __   ___ _ __ __ _| |  ___  ___| |_| |_(_)_ __   __ _ ___
-- | |  _ / _ \ '_ \ / _ \ '__/ _` | | / __|/ _ \ __| __| | '_ \ / _` / __|
-- | |_| |  __/ | | |  __/ | | (_| | | \__ \  __/ |_| |_| | | | | (_| \__ \
--  \____|\___|_| |_|\___|_|  \__,_|_| |___/\___|\__|\__|_|_| |_|\__, |___/
--                                                               |___/


vim.cmd('syntax enable')
vim.o.autoindent = true
vim.o.smartindent = true
vim.o.autoread = true -- Automatically read external changes
vim.o.scrolloff = 1 -- Leave margin when scrolling
vim.o.sidescrolloff = 5

if not string.find(vim.o.display, 'lastline') then
  vim.o.display = vim.o.display .. 'lastline'
end

-- Allow buffer switching without saving
vim.o.hidden = true
-- Substitute commands to substitute all occurrences by default
vim.o.gdefault = true

-- Magic to make autoread actually work
-- Trigger autoread when changing buffers while inside vim:
vim.cmd([[
  augroup general_settings
    autocmd!
    autocmd FocusGained,BufEnter * if !bufexists("[Command Line]") | :checktime | endif
    autocmd CursorHold * if !bufexists("[Command Line]") | checktime | endif
  augroup END
]])

function TryOmnicomplete()
  -- Simulate pressing 'a' to trigger omnicompletion
  vim.api.nvim_feedkeys('a', 'n', true)
  
  -- Check if omnifunc is set
  if vim.api.nvim_eval('&omnifunc') == '' then
    -- If omnifunc is not set, do nothing
  else
    -- If omnifunc is set, trigger omnicompletion
    vim.api.nvim_feedkeys('<C-x><C-o>', 'n', true)
  end
end

vim.cmd([[
  augroup scala_filetype
    autocmd!
    autocmd BufRead,BufNewFile *.sbt set filetype=scala
  augroup END
]])


-- jsoc comment syntax highlighting support
vim.cmd([[
  augroup json_syntax
    autocmd!
    autocmd FileType json syntax match Comment +\/\/.\+$+
  augroup END
]])

-- =========== filetypes ===========

vim.cmd([[
augroup filetypes
  autocmd BufNewFile,BufRead *.gson      setlocal ft=groovy
  autocmd BufNewFile,BufRead *.ts        setlocal ft=typescript
  autocmd Filetype groovy                setlocal makeprg=./gradlew\ test
  autocmd BufRead,BufNewFile Vagrantfile set filetype=ruby
  autocmd BufRead,BufNewFile Jenkinsfile set filetype=groovy
  autocmd FileType json                  nnoremap <buffer> <leader>f :%!python -m json.tool<cr>
  autocmd FileType xml                   nnoremap <buffer> <leader>f :%!xmllint --format -<cr>

 " uses nvim-metals
  autocmd FileType scala                 nnoremap <buffer> <leader>f :Neoformat<cr>
  autocmd Filetype scala                 setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd Filetype scala                 inoremap <silent> <BS> <BS><ESC>:call TryOmnicomplete()<CR>
  autocmd FileType python                nnoremap <buffer> <leader>f :Neoformat<cr>
  autocmd Filetype python                setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd FileType clojure               nnoremap <buffer> <leader>f :silent :!cljfmt fix %:p<cr>:edit<cr>
  autocmd FileType clojure               setlocal iskeyword-=. " Break words on .
  autocmd FileType clojure               setlocal iskeyword-=/ " Break words on /
  autocmd BufWritePre *.clj              %s/\s\+$//e

  autocmd FileType fish compiler fish
  " Set this to have long lines wrap inside comments.
  autocmd FileType fish                  setlocal textwidth=79
  autocmd Filetype groovy                setlocal tabstop=4 shiftwidth=4
  autocmd FileType haskell               setlocal tabstop=4
  autocmd Filetype yaml                  setlocal cursorcolumn

  autocmd FileType typescript            nnoremap <buffer> <leader>f :Prettier<cr>
  autocmd FileType typescript            setlocal tabstop=2 shiftwidth=2
  autocmd Filetype ts                    setlocal tabstop=2 shiftwidth=2
  autocmd Filetype ts                    setlocal filetype=typescript
  autocmd Filetype ts                    setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd Filetype tsx                   setlocal filetype=typescript.tsx
  autocmd Filetype tsx                   setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd Filetype typescript.tsx        setlocal filetype=typescript.tsx
  autocmd Filetype typescript.tsx        setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd FileType typescript.tsx        nnoremap <buffer> <leader>f :Prettier<cr>
  autocmd FileType vue                   nnoremap <buffer> <leader>f :Prettier<cr>
  autocmd Filetype vue                   setlocal omnifunc=v:lua.vim.lsp.omnifunc
  autocmd BufRead,BufNewFile *.avdl      setlocal filetype=avdl

  autocmd Filetype snippets              setlocal expandtab

  " Automatically remove trailing whitespace from markdown files on save
  autocmd BufWritePre *.md               %s/\s\+$//e
augroup END
]])

vim.o.completeopt = 'noinsert,menuone'

-- =========== vim-prettier ===========
-- Use .prettierrc, don't use custom settings from vim-prettieri
vim.g['prettier#config#config_precedence'] = 'prefer-file'

-- =========== search ===========
vim.o.grepprg = 'ag\\ $*'

-- Fuzzy finding
-- :b <substring of open buffer> to jump to buffer
-- :find <substring of open buffer>
vim.o.path = vim.o.path .. ',**'

-- Tab completion behavior
vim.o.wildmode = 'longest,list,full'

vim.o.wildmenu = true


-- =========== Splits ===========
vim.cmd([[

augroup splits
    autocmd!
    autocmd VimResized * wincmd =
augroup END
]])

vim.o.splitbelow=true
vim.o.splitright=true

-- =========== Tabs and spaces  ===========
vim.o.expandtab=true                          -- insert space characters whenever the tab key is pressed,
vim.o.tabstop=2                          -- the number of space characters that will be inserted when the tab key is pressed
vim.o.shiftwidth=2                       -- the number of space characters inserted for indentation with the reindent operations (<< and >>)
vim.o.softtabstop=0
vim.o.list=true                               -- Display tabs and trailing spaces
vim.o.listchars='tab:▸–,trail:·'


-- TODO convert to lua
vim.cmd([[
  set listchars=tab:▸–,trail:·
  set listchars+=nbsp:⦸                  " CIRCLED REVERSE SOLIDUS (U+29B8, UTF-8: E2 A6 B8)
]])

vim.o.joinspaces=false                       -- don't autoinsert two spaces after '.', '?', '!' for join command

-- Set color of listchars
vim.cmd('highlight ExtraWhitespace ctermbg=red guibg=red')
vim.cmd('match ExtraWhitespace /\\s\\+$/')

-- =========== Numbering  ===========
vim.o.number=true
vim.o.relativenumber=true
vim.o.ruler=true
vim.o.showcmd=true
EOF

 " _  __
" | |/ /___ _   _   _ __ ___   __ _ _ __  ___
" | ' // _ \ | | | | '_ ` _ \ / _` | '_ \/ __|
" | . \  __/ |_| | | | | | | | (_| | |_) \__ \
" |_|\_\___|\__, | |_| |_| |_|\__,_| .__/|___/
 "          |___/                  |_|

" completion / omnifunc
inoremap <expr> <s-tab>       pumvisible() ? "\<C-p>" : "\<s-tab>"
inoremap <expr> <cr>       pumvisible() ? "\<C-y>" : "\<cr>"

" Fix error which freezes UI: https://github.com/neovim/neovim/issues/14433 "
let g:omni_sql_default_compl_type = 'syntax'

function! ShowMappings()
  :redir @a
  :silent map
  :redir END
  :vnew
  :put a
endfunction


 " TODO c-enter compile & RUN
inoremap <A-j> <Esc>:m .+1<CR>==gi
inoremap <A-k> <Esc>:m .-2<CR>==gi
nnoremap <A-j> :m .+1<CR>==
nnoremap <A-k> :m .-2<CR>==
vnoremap <A-j> :m '>+1<CR>gv=gv
vnoremap <A-k> :m '<-2<CR>gv=gv


 " lsp
if has('nvim-0.5')
  nnoremap <silent> <leader>cc    <cmd>lua vim.lsp.buf.definition()<CR>
  nnoremap <silent> K             <cmd>lua vim.lsp.buf.hover()<CR>
  nnoremap <silent> <leader>ci    <cmd>lua vim.lsp.buf.implementation()<CR>
  nnoremap <silent> <c-b>         <cmd>lua vim.lsp.buf.implementation()<CR>
  nnoremap <silent> <s-k>         <cmd>lua vim.lsp.buf.signature_help()<CR>
  nnoremap <silent> <leader>ct    <cmd>lua vim.lsp.buf.type_definition()<CR>
  nnoremap <silent> <leader>cr    <cmd>lua vim.lsp.buf.references()<CR>

  " This is actually <c-7>
  nnoremap <silent>             <cmd>lua vim.lsp.buf.references()<CR>
  nnoremap <silent> <F7>          <cmd>lua vim.lsp.buf.references()<CR>
  nnoremap <silent> <leader>ce    <cmd>lua vim.lsp.buf.rename()<CR>
  nnoremap <silent> <F8>          <cmd>lua vim.lsp.buf.rename()<CR>
  nnoremap <silent> g0            <cmd>lua vim.lsp.buf.document_symbol()<CR>
  nnoremap <silent> gW            <cmd>lua vim.lsp.buf.workspace_symbol()<CR>
  nnoremap <silent> gD            <cmd>lua vim.lsp.buf.definition()<CR>
  nnoremap <silent> gd            <cmd>lua vim.lsp.buf.definition()<CR>
  nnoremap <silent> <leader>ca    <cmd>lua vim.lsp.buf.code_action()<CR>
  nnoremap <silent> <c-a>         <cmd>lua vim.lsp.buf.code_action()<CR>

  " Execute code_action() if not in quickfix
  function! CodeAction()
    if &buftype ==# 'quickfix'
      execute "normal! \<CR>"
    else
      :lua vim.lsp.buf.code_action()
    endif
  endfunction

  " '' is shift + enter
  nnoremap <silent>  :call CodeAction()<CR>

  nnoremap <silent> <leader>cn    <cmd>lua vim.diagnostic.goto_next()<CR>
  nnoremap <silent> <c-n>         <cmd>lua vim.diagnostic.goto_next()<CR>
  nnoremap <silent> <c-p>         <cmd>lua vim.diagnostic.goto_prev()<CR>
endif


" Copy buffer
nnoremap <leader>x :!xclip -selection clipboard %:p<cr>

function! PrintBuffers()
  let clipboardContent = system('xclip -o --selection clipboard')
  call feedkeys("acliboard content:\<esc>o")
  call feedkeys(clipboardContent)

  let primaryContent = system('xclip -o --selection primary')
  call feedkeys("\<cr>primary content:\<esc>o")
  call feedkeys(primaryContent)

  let secondaryContent = system('xclip -o --selection secondary')
  call feedkeys("\<cr>secondary content:\<esc>o")
  call feedkeys(secondaryContent)
  call feedkeys("\<esc>")
endfunction

nnoremap <leader>p :call PrintBuffers()<cr>

" Jump to tag under cursor
nnoremap <leader>i <C-]>zz

" Jump back up the tag stack
nnoremap <leader>u <C-t>zz

" Go to alternate file
nnoremap <leader><leader> <C-^>

"nnoremap <C-W> :w<CR>

inoremap <C-Space> <C-x><C-o>

nnoremap <leader>r :source ~/.vimrc<CR>
nnoremap <leader>k :NERDTreeFind<CR>
nnoremap <leader>q :NERDTreeClose<CR>:q<CR>
nnoremap <leader>bd :bdelete<CR>
nnoremap <leader>d :bd<CR>

" replace and jump
nnoremap c* *Ncgn
nnoremap gs :%s//<Left>
vnoremap gs :s/\%V/<Left>

" Git
nnoremap <leader>0 :Git blame<CR>

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
" unnamedplus is for the <C-c> <C-v> mappings used in many GUI programs
set clipboard+=unnamed,unnamedplus

nnoremap <leader>cp           olog.info("<esc>"*pa: ${<esc>"*pa}")<esc>

noremap <down>   <c-w>J
noremap <up>     <c-w>K
noremap <left>   <c-w>H
noremap <right>  <c-w>L


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
noremap <tab>    <C-W>w
noremap <s-tab>    <C-W>W

" the tab mapping above ruins <c-i>, <tab> since they are the same thing
" apparently https://stackoverflow.com/questions/24967213/vim-mapping-of-c-i-in-insert-mode
nnoremap <c-i> <c-i>

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

function! ToHtml()
  call system('pandoc '. expand('%') . ' > ' . expand('%') . '.html')
endfunction

function! ToDoc()
  call system('pandoc '. expand('%') . ' > ' . expand('%') . '.doc')
endfunction

function! SortShoppingList()
  silent let result=system('s shoppinglist sort ' . expand('%:p'))
  " cleanup buffer
  :%delete _
  " insert result
  call setline(1, split(result, '\n'))
endfunction

nnoremap <leader>hh :call ToHtml()<cr>
nnoremap <leader>wm :!s wiki html<cr>

nnoremap <leader>wl :call SortShoppingList()<cr>
nnoremap <leader>we :e ~/dev/shoppinglist_sorter/reference.csv<cr>

" Make sure 0 works in normal mode when using colemaknordic layout
nnoremap ü 0
