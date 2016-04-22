package edu.unq.vainilla.core.input

import com.badlogic.gdx.InputProcessor
import edu.unq.vainilla.core.{GameScene, VanillaGame}

trait InputHandler extends InputProcessor {
  /** Called when a key was typed
    *
    * @param character The character
    * @return whether the input was processed */
  def keyTyped(character: Char): Boolean = false

  /** Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
    *
    * @return whether the input was processed */
  def mouseMoved(screenX: Int, screenY: Int): Boolean = false

  /** Called when a key was pressed
    *
    * @param keycode one of the constants in { @link Input.Keys}
    * @return whether the input was processed */
  def keyDown(keycode: Int): Boolean = false

  /** Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on iOS.
    *
    * @param screenX The x coordinate, origin is in the upper left corner
    * @param screenY The y coordinate, origin is in the upper left corner
    * @param pointer the pointer for the event.
    * @param button  the button
    * @return whether the input was processed */
  def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  /** Called when a key was released
    *
    * @param keycode one of the constants in { @link Input.Keys}
    * @return whether the input was processed */
  def keyUp(keycode: Int): Boolean = false

  /** Called when the mouse wheel was scrolled. Will not be called on iOS.
    *
    * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
    * @return whether the input was processed. */
  def scrolled(amount: Int): Boolean = false

  /** Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on iOS.
    *
    * @param pointer the pointer for the event.
    * @param button  the button
    * @return whether the input was processed */
  def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false

  /** Called when a finger or the mouse was dragged.
    *
    * @param pointer the pointer for the event.
    * @return whether the input was processed */
  def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
}

trait InputSupport extends GameScene {
  val inputHandler: InputHandler
}

class SimpleInputHandler extends InputHandler

object SceneDelegatorInputHandler extends InputHandler {
  override def keyTyped(character: Char): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.keyTyped(character)
    case scene: InputHandler => scene.keyTyped(character)
    case scene: InputProcessor => scene.keyTyped(character)
    case _ => false
  }


  override def mouseMoved(screenX: Int, screenY: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.mouseMoved(screenX, screenY)
    case scene: InputHandler => scene.mouseMoved(screenX, screenY)
    case scene: InputProcessor => scene.mouseMoved(screenX, screenY)
    case _ => false
  }

  override def keyDown(keycode: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.keyDown(keycode)
    case scene: InputHandler => scene.keyDown(keycode)
    case scene: InputProcessor => scene.keyDown(keycode)
    case _ => false
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.touchDown(screenX, screenY, pointer, button)
    case scene: InputHandler => scene.touchDown(screenX, screenY, pointer, button)
    case scene: InputProcessor => scene.touchDown(screenX, screenY, pointer, button)
    case _ => false
  }

  override def keyUp(keycode: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.keyUp(keycode)
    case scene: InputHandler => scene.keyUp(keycode)
    case scene: InputProcessor => scene.keyUp(keycode)
    case _ => false
  }

  override def scrolled(amount: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.scrolled(amount)
    case scene: InputHandler => scene.scrolled(amount)
    case scene: InputProcessor => scene.scrolled(amount)
    case _ => false
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.touchUp(screenX, screenY, pointer, button)
    case scene: InputHandler => scene.touchUp(screenX, screenY, pointer, button)
    case scene: InputProcessor => scene.touchUp(screenX, screenY, pointer, button)
    case _ => false
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = VanillaGame.currentScene match {
    case scene: InputSupport => scene.inputHandler.touchDragged(screenX, screenY, pointer)
    case scene: InputHandler => scene.touchDragged(screenX, screenY, pointer)
    case scene: InputProcessor => scene.touchDragged(screenX, screenY, pointer)
    case _ => false
  }
}