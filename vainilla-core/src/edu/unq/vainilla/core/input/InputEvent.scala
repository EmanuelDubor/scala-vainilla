package edu.unq.vainilla.core.input

class InputEvent

case class KeyTyped(char: Char) extends InputEvent

case class MouseMoved(screenX: Int, screenY: Int) extends InputEvent

case class KeyDown(keycode: Int) extends InputEvent

case class TouchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) extends InputEvent

case class KeyUp(keycode: Int) extends InputEvent

case class Scrolled(amount: Int) extends InputEvent

case class TouchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) extends InputEvent

case class TouchDragged(screenX: Int, screenY: Int, pointer: Int) extends InputEvent