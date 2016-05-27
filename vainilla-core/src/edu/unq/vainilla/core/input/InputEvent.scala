package edu.unq.vainilla.core.input

import edu.unq.vainilla.core.cords.ScreenCord

class InputEvent

case class KeyTyped(char: Char) extends InputEvent

case class KeyDown(keycode: Int) extends InputEvent

case class KeyUp(keycode: Int) extends InputEvent

case class Scrolled(amount: Int) extends InputEvent

case class MouseMoved(screenCords: ScreenCord) extends InputEvent

case class TouchDown(screenCords: ScreenCord, pointer: Int, button: Int) extends InputEvent

case class TouchUp(screenCords: ScreenCord, pointer: Int, button: Int) extends InputEvent

case class TouchDragged(screenCords: ScreenCord, pointer: Int) extends InputEvent