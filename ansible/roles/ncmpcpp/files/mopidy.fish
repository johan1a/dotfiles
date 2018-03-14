#!/usr/bin/fish
preset-password
set secrets (gpg --use-agent -dq $HOME/.secrets/spotify.gpg)
/usr/bin/mopidy $secrets

