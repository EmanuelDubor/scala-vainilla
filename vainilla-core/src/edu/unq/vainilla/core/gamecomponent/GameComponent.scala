package edu.unq.vainilla.core.gamecomponent

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import edu.unq.vainilla.core.appearence.Appearence
import edu.unq.vainilla.core.cords.Cord2d
import edu.unq.vainilla.core.cords.CordImplicits._
import edu.unq.vainilla.core.utils.{TreeLeaf, TreeLike, TreeNode}

trait GameComponent extends TreeLike[GameComponent] {

  var visible: Boolean = true

  var position: Cord2d = (0, 0)

  def x = position.x

  def x_=[T](newX: T)(implicit n: Numeric[T]) = position = (newX, y)

  def y = position.y

  def y_=[T](newY: T)(implicit n: Numeric[T]) = position = (x, newY)

  def render(implicit spriteBatch: SpriteBatch): Unit

}

class SimpleGameComponent(appearence: Appearence) extends GameComponent with TreeLeaf[GameComponent] {
  def render(implicit spriteBatch: SpriteBatch): Unit = if (visible) spriteBatch.draw(appearence.textureRegion, position.x, position.y)
}

class GameComponentGroup extends GameComponent with TreeNode[GameComponent] {
  def render(implicit spriteBatch: SpriteBatch): Unit = if (visible) childs.foreach(_.render)
}