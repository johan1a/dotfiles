local filetypes_group = vim.api.nvim_create_augroup("filetypes", { clear = true })

vim.api.nvim_create_autocmd({ "BufRead", "BufNewFile" }, {
    pattern = "*.gson",
  callback = function()
        vim.bo.filetype = "groovy"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "BufRead", "BufNewFile" }, {
    pattern = "*.ts",
    callback = function()
        vim.bo.filetype = "typescript"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "ts",
    callback = function()
        vim.bo.filetype = "typescript"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "json",
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":%!python -m json.tool<cr>", { noremap = true })
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "xml",
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":%!xmllint --format<cr>", { noremap = true })
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "BufRead", "BufNewFile" }, {
    pattern = "*.sbt",
    callback = function()
        vim.bo.filetype = "scala"
    end,
    group = filetypes_group,
})
-- uses nvim-metals
vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "scala",
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":Neoformat<cr>", { noremap = true })
        vim.bo.omnifunc = "v:lua.vim.lsp.omnifunc"
        vim.api.nvim_set_keymap("i", "<BS>", "<BS><C-x><C-o>", { silent = true, noremap = true })
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "python",
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":Neoformat<cr>", { noremap = true })
        vim.bo.omnifunc = "v:lua.vim.lsp.omnifunc"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "clojure",
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":!cljfmt fix %:p<cr>:edit<cr>", { silent = true, noremap = true })
        -- Break words on . and /
        vim.opt_local.iskeyword:remove(".")
        vim.opt_local.iskeyword:remove("/")
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "BufWritePre" }, {
    pattern = { "*.clj", "*.md" },
    callback = function()
        vim.cmd("%s/\\s\\+$//e", "n", true)
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "fish",
    callback = function()
        -- " Set this to have long lines wrap inside comments.
        vim.opt_local.textwidth = 79
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "lua",
    callback = function()
        require("lspconfig").lua_ls.setup({})
        vim.bo.omnifunc = "v:lua.vim.lsp.omnifunc"
        vim.api.nvim_set_keymap("n", "<leader>f", ":Neoformat<cr>", { noremap = true })
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "groovy",
    callback = function()
        vim.opt_local.tabstop = 4
        vim.opt_local.shiftwidth = 4
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "haskell",
    callback = function()
        vim.opt_local.tabstop = 4
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "yaml",
    callback = function()
        vim.opt_local.cursorcolumn = true
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = { "typescript", "typescript.tsx", "vue" },
    callback = function()
        vim.api.nvim_set_keymap("n", "<leader>f", ":Prettier<cr>", { noremap = true })
        vim.bo.omnifunc = "v:lua.vim.lsp.omnifunc"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "tsx",
    callback = function()
        vim.bo.filetype = "typescript.tsx"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "BufRead", "BufNewFile" }, {
    pattern = "*.avdl",
    callback = function()
        vim.bo.filetype = "avdl"
    end,
    group = filetypes_group,
})

vim.api.nvim_create_autocmd({ "FileType" }, {
    pattern = "snippets",
    callback = function()
        vim.opt_local.expandtab = true
    end,
    group = filetypes_group,
})
return
