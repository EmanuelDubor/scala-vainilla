package edu.unq.vainilla.arkanoid

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import edu.unq.vainilla.core.GameScene

class ArkanoidDemo extends GameScene {
  var img: Texture = _

  override def create {
    img = new Texture("badlogic.jpg")
  }

  def render(batch: SpriteBatch) {
    batch.draw(img, 0, 0)
  }

}
