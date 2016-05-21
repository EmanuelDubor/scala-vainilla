package edu.unq.vainilla.core.gamescene

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import edu.unq.vainilla.core.utils.{LifeCycle, SortedTreeNode, TreeLeaf, TreeLike}

trait GameScene extends LifeCycle with TreeLike[GameScene] with Ordered[GameScene] {

  private var _z = 0

  def z = _z

  def z_=(z: Int) = {
    parent match {
      case Some(scene: LayeredGameScene) => scene.resort
      case _ =>
    }
    _z = z
  }

  def compare(that: GameScene): Int = this.z - that.z

  def render(implicit spriteBatch: SpriteBatch, deltaTime: Float)

}

trait SimpleGameScene extends GameScene with TreeLeaf[GameScene] {

  implicit var camera: OrthographicCamera = _

  override def create: Unit = {
    super.create
    camera = new OrthographicCamera
    camera.setToOrtho(false)
  }

  def render(implicit spriteBatch: SpriteBatch, deltaTime: Float): Unit = {
    camera.update
    spriteBatch.setProjectionMatrix(camera.combined)
  }
}

trait LayeredGameScene extends GameScene with SortedTreeNode[GameScene] {

  def render(implicit spriteBatch: SpriteBatch, deltaTime: Float): Unit = {
    childs.foreach(_.render)
  }
}
