package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import edu.unq.vainilla.core.configuration.Configuration

object VanillaGame extends ApplicationAdapter {

  var mainScene: GameScene = _
  var currentScene: GameScene = _
  var config: Configuration = _
  var batch: SpriteBatch = _

  override def create {
    mainScene.create
    currentScene = mainScene
    batch = new SpriteBatch
  }

  override def render {
    Gdx.gl.glClearColor(
      config.initialBackgroundColor.r,
      config.initialBackgroundColor.g,
      config.initialBackgroundColor.b,
      config.initialBackgroundColor.a
    )
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin
    currentScene.render(batch)
    batch.end
  }

}
