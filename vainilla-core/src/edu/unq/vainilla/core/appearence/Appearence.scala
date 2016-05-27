package edu.unq.vainilla.core.appearence

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Appearence(val textureRegion: TextureRegion)

object Appearence {
  def apply(textureRegion: TextureRegion): Appearence = new Appearence(textureRegion)

  def apply(texture: Texture): Appearence = new Appearence(new TextureRegion(texture))
}
