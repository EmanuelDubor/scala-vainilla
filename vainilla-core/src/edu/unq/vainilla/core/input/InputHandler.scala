package edu.unq.vainilla.core.input

import com.badlogic.gdx.InputProcessor
import edu.unq.vainilla.core.gamescene.{GameScene, LayeredGameScene}

object VainillaInputProcessor extends InputProcessor {

  import edu.unq.vainilla.core.VainillaGame.inputHandler
  import edu.unq.vainilla.core.cords.CordImplicits._

  def process[E <: InputEvent](event: E, partialFunction: PartialFunction[E, Unit]): Boolean = {
    val canHandle = partialFunction.isDefinedAt(event)
    if (canHandle) {
      partialFunction(event)
    }
    canHandle
  }

  def keyTyped(character: Char): Boolean =
    process(KeyTyped(character), inputHandler.keyTyped)

  def mouseMoved(screenX: Int, screenY: Int): Boolean =
    process(MouseMoved((screenX, screenY)), inputHandler.mouseMoved)

  def keyDown(keycode: Int): Boolean =
    process(KeyDown(keycode), inputHandler.keyDown)

  def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
    process(TouchDown((screenX, screenY), pointer, button), inputHandler.touchDown)

  def keyUp(keycode: Int): Boolean =
    process(KeyUp(keycode), inputHandler.keyUp)

  def scrolled(amount: Int): Boolean =
    process(Scrolled(amount), inputHandler.scrolled)

  def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean =
    process(TouchUp((screenX, screenY), pointer, button), inputHandler.touchUp)

  def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean =
    process(TouchDragged((screenX, screenY), pointer), inputHandler.touchDragged)
}

trait InputHandler {

  def doNothing[T <: InputEvent]: PartialFunction[T, Unit] = {
    case _ =>
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

trait InputSupport extends GameScene with InputHandler {
  val inputHandler: InputHandler

  def keyTyped: PartialFunction[KeyTyped, Unit] = inputHandler.keyTyped

  def mouseMoved: PartialFunction[MouseMoved, Unit] = inputHandler.mouseMoved

  def keyDown: PartialFunction[KeyDown, Unit] = inputHandler.keyDown

  def touchDown: PartialFunction[TouchDown, Unit] = inputHandler.touchDown

  def keyUp: PartialFunction[KeyUp, Unit] = inputHandler.keyUp

  def scrolled: PartialFunction[Scrolled, Unit] = inputHandler.scrolled

  def touchUp: PartialFunction[TouchUp, Unit] = inputHandler.touchUp

  def touchDragged: PartialFunction[TouchDragged, Unit] = inputHandler.touchDragged
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

  import edu.unq.vainilla.core.VainillaGame.currentScene

  private def compose[T <: InputEvent](scene: LayeredGameScene, f: InputHandler => PartialFunction[T, Unit]) = {
    scene.
      flatten.
      collect { case layer: InputHandler => layer }.
      map(f(_)).
      foldLeft(doNothing[T]) {
        _.orElse(_)
      }
  }

  def keyTyped: PartialFunction[KeyTyped, Unit] = currentScene match {
    case scene: InputHandler => scene.keyTyped
    case scene: LayeredGameScene => compose(scene, _.keyTyped)
    case _ => doNothing
  }

  def mouseMoved: PartialFunction[MouseMoved, Unit] = currentScene match {
    case scene: InputHandler => scene.mouseMoved
    case scene: LayeredGameScene => compose(scene, _.mouseMoved)
    case _ => doNothing
  }

  def keyDown: PartialFunction[KeyDown, Unit] = currentScene match {
    case scene: InputHandler => scene.keyDown
    case scene: LayeredGameScene => compose(scene, _.keyDown)
    case _ => doNothing
  }

  def touchDown: PartialFunction[TouchDown, Unit] = currentScene match {
    case scene: InputHandler => scene.touchDown
    case scene: LayeredGameScene => compose(scene, _.touchDown)
    case _ => doNothing
  }

  def keyUp: PartialFunction[KeyUp, Unit] = currentScene match {
    case scene: InputHandler => scene.keyUp
    case scene: LayeredGameScene => compose(scene, _.keyUp)
    case _ => doNothing
  }

  def scrolled: PartialFunction[Scrolled, Unit] = currentScene match {
    case scene: InputHandler => scene.scrolled
    case scene: LayeredGameScene => compose(scene, _.scrolled)
    case _ => doNothing
  }

  def touchUp: PartialFunction[TouchUp, Unit] = currentScene match {
    case scene: InputHandler => scene.touchUp
    case scene: LayeredGameScene => compose(scene, _.touchUp)
    case _ => doNothing
  }

  def touchDragged: PartialFunction[TouchDragged, Unit] = currentScene match {
    case scene: InputHandler => scene.touchDragged
    case scene: LayeredGameScene => compose(scene, _.touchDragged)
    case _ => doNothing
  }
}