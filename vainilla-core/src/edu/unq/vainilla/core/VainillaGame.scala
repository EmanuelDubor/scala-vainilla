package edu.unq.vainilla.core

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputProcessor}
import edu.unq.vainilla.core.configuration.Configuration
import edu.unq.vainilla.core.gamescene.GameScene
import edu.unq.vainilla.core.input.InputHandler
import edu.unq.vainilla.core.utils.LifeCycle

object VainillaGame extends ApplicationAdapter with LifeCycle {

  var mainScene: GameScene = _
  var currentScene: GameScene = _
  var config: Configuration = _
  var inputHandler: InputHandler = _
  var gdxInputProcessor: InputProcessor = _
  implicit var spriteBatch: SpriteBatch = _

  private var _updateBeforeRender = true

  override def create {
    super.create
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
    implicit val deltaTime = Math.min(Gdx.graphics.getDeltaTime, 1 / 30)
    if (_updateBeforeRender) currentScene.update
    spriteBatch.begin
    currentScene.render
    spriteBatch.end
    if (!_updateBeforeRender) currentScene.update
  }

  override def dispose: Unit = {
    super.dispose
    spriteBatch.dispose
  }

  def updateBeforeRender = _updateBeforeRender = true

  def renderBeforeUpdate = _updateBeforeRender = false

}
