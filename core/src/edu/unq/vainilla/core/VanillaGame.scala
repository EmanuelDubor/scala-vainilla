package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{ApplicationAdapter, Gdx}

class VanillaGame(mainScene: GameScene) extends ApplicationAdapter {

  var batch: SpriteBatch = _

  override def create {
    mainScene.create
    batch = new SpriteBatch
  }

  override def render {
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin
    mainScene.render(batch)
    batch.end
  }

}
