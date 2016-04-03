package edu.unq.vainilla.arkanoid

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, Texture}
import com.badlogic.gdx.{ApplicationAdapter, Gdx}

/**
  * Created by Emanuel on 03/04/2016.
  */
class ArkanoidDemo extends ApplicationAdapter {
  var batch: SpriteBatch = _
  var img: Texture = _

  override def create {
    batch = new SpriteBatch
    img = new Texture("badlogic.jpg")
  }

  override def render {
    Gdx.gl.glClearColor(1, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    batch.begin
    batch.draw(img, 0, 0)
    batch.end
  }
}
