package edu.unq.vainilla.core.appearence

import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import edu.unq.vainilla.core.cords.Cord2d

trait Appearence {
  def render(position: Cord2d)(implicit spriteBatch: SpriteBatch): Unit
}

class TextureRegionAppearence(textureRegion: TextureRegion) extends Appearence {
  def render(position: Cord2d)(implicit spriteBatch: SpriteBatch): Unit = {
    spriteBatch.draw(textureRegion, position.x, position.y)
  }
}

class TextureAppearence(texture: Texture) extends Appearence {
  def render(position: Cord2d)(implicit spriteBatch: SpriteBatch): Unit = {
    spriteBatch.draw(texture, position.x, position.y)
  }
}

object InvisibleAppearence extends Appearence {
  def render(position: Cord2d)(implicit spriteBatch: SpriteBatch): Unit = {}
}

object AppearenceDSL {

  var defaultPixmapFormat = Pixmap.Format.RGBA8888

  implicit def textureToAppearence(texture: Texture): Appearence = Texture(texture)

  implicit def textureRegionToAppearence(textureRegion: TextureRegion): Appearence = Texture(textureRegion)

  implicit def pixmapToAppearence(pixmap: Pixmap): Appearence = Texture(pixmap)

  def Texture(textureRegion: TextureRegion): Appearence = new TextureRegionAppearence(textureRegion)

  def Texture(texture: Texture): Appearence = new TextureAppearence(texture)

  def Texture(pixmap: Pixmap): Appearence = new TextureAppearence(new Texture(pixmap))

  def Invisible: Appearence = InvisibleAppearence

  def pixmapOperation(pixmapWidth: Int, pixmapHeight: Int, op: Pixmap => Unit): Appearence = {
    val pixmap = new Pixmap(pixmapWidth, pixmapHeight, defaultPixmapFormat)
    op(pixmap)
    val app: Appearence = pixmap
    pixmap.dispose
    app
  }

  private def simplePixmapOperation(pixmapWidth: Int, pixmapHeight: Int, color: Color, op: Pixmap => Unit) =
    pixmapOperation(pixmapWidth, pixmapHeight, { pixmap =>
      pixmap.setColor(color)
      op(pixmap)
    })


  def SolidRectangle(width: Int, height: Int, color: Color) = simplePixmapOperation(width, height, color, _.fill)

  def HollowRectangle(width: Int, height: Int, color: Color) = simplePixmapOperation(width, height, color, _.drawRectangle(0, 0, width, height))

  def SolidCircle(radius: Int, color: Color) = simplePixmapOperation(radius * 2, radius * 2, color, _.fillCircle(radius, radius, radius))

  def HollowCircle(radius: Int, color: Color) = simplePixmapOperation(radius * 2, radius * 2, color, _.drawCircle(radius, radius, radius))

  def SolidTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) = {
    val points = List(p1, p2, p3)
    val pixmapWidth = points.maxBy(_._1)._1
    val pixmapHeight = points.maxBy(_._2)._2
    val operation: Pixmap => Unit = _.fillTriangle(p1._1, p1._2, p2._1, p2._2, p3._1, p3._2)
    simplePixmapOperation(pixmapWidth, pixmapHeight, color, operation)
  }

  def HollowTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) = {
    val points = List(p1, p2, p3)
    val pixmapWidth = points.maxBy(_._1)._1
    val pixmapHeight = points.maxBy(_._2)._2
    val operation: Pixmap => Unit = { pixmap =>
      pixmap.drawLine(p1._1, p1._2, p2._1, p2._2)
      pixmap.drawLine(p1._1, p1._2, p3._1, p3._2)
      pixmap.drawLine(p2._1, p2._2, p3._1, p3._2)
    }
    simplePixmapOperation(pixmapWidth, pixmapHeight, color, operation)
  }

  def Line(p: (Int, Int), color: Color) = simplePixmapOperation(p._1, p._2, color, _.drawLine(0, 0, p._1, p._2))

}
