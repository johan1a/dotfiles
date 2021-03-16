# Manual steps needed

## pass

```
gpg -k
# copy personal keygrip
pass init $keygrip
```

## Mail
```
# Insert bridge specific password
pass insert neomutt/password

# Insert email
pass insert neomutt/email

# Create ~/.local.fish:
#!/usr/bin/env fish
set -x EMAIL themail@mail.com

# For calendar sync
pass insert owncloud/password
pass insert owncloud/caldav_url
#https://HOST:PORT/remote.php/dav/
```


## TODO
```
#Is this necessary anymore?
systemctl enable systemd-timesyncd

xdg-mime default firefox.desktop text/html
xdg-mime default firefox.desktop x-scheme-handler/http
xdg-mime default firefox.desktop x-scheme-handler/https
xdg-mime default firefox.desktop x-scheme-handler/about
```

Use pass neomutt/email instead of EMAIL
