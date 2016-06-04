package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{ApplicationAdapter, Gdx}
import edu.unq.vainilla.core.configuration.Configuration
import edu.unq.vainilla.core.gamescene.GameScene
import edu.unq.vainilla.core.utils.LifeCycle

object VainillaGame extends ApplicationAdapter with LifeCycle {

  var currentScene: GameScene = _
  var config: Configuration = _

  implicit var spriteBatch: SpriteBatch = _

  override def create {
    super.create
    Gdx.input.setInputProcessor(config.inputProcessor(config.inputHandler))
    currentScene = config.mainScene
    currentScene.create
    spriteBatch = new SpriteBatch
  }

  override def render {
    Gdx.gl.glClearColor(
      config.initialBackgroundColor.r,
      config.initialBackgroundColor.g,
      config.initialBackgroundColor.b,
      config.initialBackgroundColor.a
    )
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    implicit val deltaTime = Math.min(Gdx.graphics.getDeltaTime, config.maxUpdateValue)
    if (config._updateBeforeRender) currentScene.update
    spriteBatch.begin
    currentScene.render
    spriteBatch.end
    if (!config._updateBeforeRender) currentScene.update
  }

  override def dispose: Unit = {
    super.dispose
    spriteBatch.dispose
  }

}
