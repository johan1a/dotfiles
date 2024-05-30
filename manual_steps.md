# Manual steps needed

## Colemak nordic

Fugly modded variant of colemak that replaces 7890- with åäöüß.

```
# add to /usr/share/X11/xkb/rules/base.list
# under ! variants
colemaknordic   us: English (Colemak Nordic)

# Switch to colemak nordic
colemaknordic

# Switch to regular colemak
colemak
```

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

## Monitor

```
To automatically enable the Dell monitor, add this udev rule to:

#/etc/udev/rules.d/95-monitor-hotplug.rules
KERNEL=="1-7", SUBSYSTEM=="usb", ATTRS{idVendor}=="413c", ATTRS{idProduct}=="8442", ENV{DISPLAY}=":0", ENV{XAUTHORITY}="/home/johan/.Xauthority", RUN+="/usr/bin/autorandr -c"
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
