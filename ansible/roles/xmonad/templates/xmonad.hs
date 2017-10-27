import XMonad
import qualified XMonad.StackSet as W
import XMonad.Util.EZConfig

main = do
    xmonad $ defaultConfig
        { terminal           = "urxvt"
        , modMask            = mod4Mask -- Win key
        , borderWidth        = 1
        , normalBorderColor  = "#000000"
        , focusedBorderColor = "#FFFFFF"
        , workspaces         = myWorkspaces
        } `additionalKeysP` myKeys

myWorkspaces = ["1", "2", "3", "4", "5", "6", "7", "8", "9"]

myKeys = [
    -- other keybindings
    ] ++

    [ (otherModMasks ++ "M-" ++ [key], action tag)
      | (tag, key) <- zip myWorkspaces "123456789"
      , (otherModMasks, action) <- [ ("", windows . W.view) -- Was W.greedyView
                                      , ("S-", windows . W.shift)]
    ]
