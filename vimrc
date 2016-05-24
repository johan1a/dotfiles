syntax enable
filetype plugin indent on
set grepprg=grep\ -nH\ $*
            
set smartindent
set cindent
set number

set noexpandtab
set copyindent
set preserveindent
set softtabstop=0
set shiftwidth=4
set tabstop=4

execute pathogen#infect()
