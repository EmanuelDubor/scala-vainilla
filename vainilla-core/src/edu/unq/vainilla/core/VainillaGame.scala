package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputProcessor}
import edu.unq.vainilla.core.configuration.Configuration
import edu.unq.vainilla.core.gamescene.GameScene
import edu.unq.vainilla.core.input.InputHandler

object VainillaGame extends ApplicationAdapter {

  var mainScene: GameScene = _
  var currentScene: GameScene = _
  var config: Configuration = _
  var inputHandler: InputHandler = _
  var gdxInputProcessor: InputProcessor = _
  implicit var spriteBatch: SpriteBatch = _

  override def create {
    Gdx.input.setInputProcessor(gdxInputProcessor)
    mainScene.create
    currentScene = mainScene
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
    implicit val deltaTime = Gdx.graphics.getDeltaTime
    spriteBatch.begin
    currentScene.render
    spriteBatch.end
  }

}
