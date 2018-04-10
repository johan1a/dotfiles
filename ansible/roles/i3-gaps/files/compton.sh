#!/usr/bin/env fish

# Terminate already running bar instances
killall -q compton

# Wait until the processes have been shut down
while pgrep -x compton >/dev/null; do sleep 1; end

# Launch compton
if os-name > /dev/null == "Ubuntu"
  compton -b
else
  compton -b --backend xrender
end


