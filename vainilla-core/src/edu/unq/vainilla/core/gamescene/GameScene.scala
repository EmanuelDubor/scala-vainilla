package edu.unq.vainilla.core.gamescene

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import edu.unq.vainilla.core.LifeCycle

import scala.collection.mutable.ListBuffer

trait GameScene extends LifeCycle {

  var z = 0

  def render(implicit spriteBatch: SpriteBatch, deltaTime: Float)

}

trait SimpleGameScene extends GameScene {

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

trait LayeredGameScene extends GameScene {

  var layers: ListBuffer[GameScene] = _

  override def create: Unit = {
    super.create
    layers = ListBuffer.empty[GameScene]
  }

  def render(implicit spriteBatch: SpriteBatch, deltaTime: Float): Unit = {
    layers.sortBy(_.z).foreach(_.render)
  }
}
