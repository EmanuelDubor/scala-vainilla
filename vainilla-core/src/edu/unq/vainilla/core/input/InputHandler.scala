package edu.unq.vainilla.core.input

import com.badlogic.gdx.InputProcessor
import edu.unq.vainilla.core.VainillaGame
import edu.unq.vainilla.core.gamescene.GameScene

object VainillaInputProcessor extends InputProcessor {
  def process[E <: InputEvent](event: E, partialFunction: PartialFunction[E, Unit]): Boolean = {
    val canHandle = partialFunction.isDefinedAt(event)
    if (canHandle) {
      partialFunction(event)
    }
    canHandle
  }

  def keyTyped(character: Char): Boolean =
    process(KeyTyped(character), VainillaGame.inputHandler.keyTyped)

  def mouseMoved(screenX: Int, screenY: Int): Boolean =
    process(MouseMoved(screenX, screenY), VainillaGame.inputHandler.mouseMoved)

  def keyDown(keycode: Int): Boolean =
    process(KeyDown(keycode), VainillaGame.inputHandler.keyDown)

  def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
    process(TouchDown(screenX, screenY, pointer, button), VainillaGame.inputHandler.touchDown)

  def keyUp(keycode: Int): Boolean =
    process(KeyUp(keycode), VainillaGame.inputHandler.keyUp)

  def scrolled(amount: Int): Boolean =
    process(Scrolled(amount), VainillaGame.inputHandler.scrolled)

  def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
    process(TouchUp(screenX, screenY, pointer, button), VainillaGame.inputHandler.touchUp)

  def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean =
    process(TouchDragged(screenX, screenY, pointer), VainillaGame.inputHandler.touchDragged)
}

trait InputHandler {

  def doNothing[T <: InputEvent]: PartialFunction[T, Unit] = {
    case _: T => Unit
  }

  /** Called when a key was typed **/
  def keyTyped: PartialFunction[KeyTyped, Unit]

  /** Called when the mouse was moved without any buttons being pressed. Will not be called on iOS. **/
  def mouseMoved: PartialFunction[MouseMoved, Unit]

  /** Called when a key was pressed **/
  def keyDown: PartialFunction[KeyDown, Unit]

  /** Called when the screen was touched or a mouse button was pressed. The button will be {@link Buttons#LEFT} on iOS. **/
  def touchDown: PartialFunction[TouchDown, Unit]

  /** Called when a key was released **/
  def keyUp: PartialFunction[KeyUp, Unit]

  /** Called when the mouse wheel was scrolled. Will not be called on iOS. **/
  def scrolled: PartialFunction[Scrolled, Unit]

  /** Called when a finger was lifted or a mouse button was released. The button will be {@link Buttons#LEFT} on iOS. **/
  def touchUp: PartialFunction[TouchUp, Unit]

  /** Called when a finger or the mouse was dragged. **/
  def touchDragged: PartialFunction[TouchDragged, Unit]
}

trait InputSupport extends GameScene {
  val inputHandler: InputHandler
}

trait SimpleInputHandler extends InputHandler {
  def keyTyped: PartialFunction[KeyTyped, Unit] = doNothing

  def mouseMoved: PartialFunction[MouseMoved, Unit] = doNothing

  def keyDown: PartialFunction[KeyDown, Unit] = doNothing

  def touchDown: PartialFunction[TouchDown, Unit] = doNothing

  def keyUp: PartialFunction[KeyUp, Unit] = doNothing

  def scrolled: PartialFunction[Scrolled, Unit] = doNothing

  def touchUp: PartialFunction[TouchUp, Unit] = doNothing

  def touchDragged: PartialFunction[TouchDragged, Unit] = doNothing
}

class BasicInputHandler extends SimpleInputHandler

object SceneDelegatorInputHandler extends InputHandler {
  def keyTyped: PartialFunction[KeyTyped, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.keyTyped
    case scene: InputSupport => scene.inputHandler.keyTyped
    case _ => doNothing
  }

  def mouseMoved: PartialFunction[MouseMoved, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.mouseMoved
    case scene: InputSupport => scene.inputHandler.mouseMoved
    case _ => doNothing
  }

  def keyDown: PartialFunction[KeyDown, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.keyDown
    case scene: InputSupport => scene.inputHandler.keyDown
    case _ => doNothing
  }

  def touchDown: PartialFunction[TouchDown, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.touchDown
    case scene: InputSupport => scene.inputHandler.touchDown
    case _ => doNothing
  }

  def keyUp: PartialFunction[KeyUp, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.keyUp
    case scene: InputSupport => scene.inputHandler.keyUp
    case _ => doNothing
  }

  def scrolled: PartialFunction[Scrolled, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.scrolled
    case scene: InputSupport => scene.inputHandler.scrolled
    case _ => doNothing
  }

  def touchUp: PartialFunction[TouchUp, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.touchUp
    case scene: InputSupport => scene.inputHandler.touchUp
    case _ => doNothing
  }

  def touchDragged: PartialFunction[TouchDragged, Unit] = VainillaGame.currentScene match {
    case scene: InputHandler => scene.touchDragged
    case scene: InputSupport => scene.inputHandler.touchDragged
    case _ => doNothing
  }
}