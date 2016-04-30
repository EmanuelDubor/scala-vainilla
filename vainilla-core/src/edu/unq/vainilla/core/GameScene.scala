package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait GameScene extends LifeCycle {

  def render(spriteBatch: SpriteBatch)

}
