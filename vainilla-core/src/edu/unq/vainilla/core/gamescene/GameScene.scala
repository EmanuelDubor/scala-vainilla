package edu.unq.vainilla.core.gamescene

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import edu.unq.vainilla.core.utils.{LifeCycle, SortedTreeNode, TreeLeaf, TreeLike}

trait GameScene extends LifeCycle with TreeLike[GameScene] with Ordered[GameScene] {
  implicit var camera: OrthographicCamera = _

  private var _z = 0

  def z = _z

  def z_=(z: Int) = {
    parent match {
      case Some(scene: LayeredGameScene) => scene.resort
      case _ =>
    }
    _z = z
  }

  override def create: Unit = {
    super.create
    camera = new OrthographicCamera
    camera.setToOrtho(false)
  }

  def compare(that: GameScene): Int = this.z - that.z

  def render(implicit spriteBatch: SpriteBatch) = {
    camera.update()
  }

  def update(implicit delta: Float) = {}

}

trait SimpleGameScene extends GameScene with TreeLeaf[GameScene] {

  override def render(implicit spriteBatch: SpriteBatch): Unit = {
    super.render
    spriteBatch.setProjectionMatrix(camera.combined)
  }
}

trait LayeredGameScene extends GameScene with SortedTreeNode[GameScene] {

  override def render(implicit spriteBatch: SpriteBatch): Unit = {
    super.render
    childs.foreach(_.render)
  }

  override def update(implicit delta: Float): Unit = {
    super.update
    childs.foreach(_.update)
  }
}

class BlankScene extends SimpleGameScene