import XMonad

main = do
    xmonad $ defaultConfig
        { terminal     =  "urxvt"
        , modMask      =  mod4Mask -- Win key
        , borderWidth  =  3
      }
