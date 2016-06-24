package edu.unq.vainilla.core.appearence

import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{Color, Pixmap, Texture}
import edu.unq.vainilla.core.appearence.AppearenceDSL._
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

class SolidRectangle(width: Int, height: Int, color: Color) extends TextureAppearence(
  simplePixmapOperation(width, height, color, _.fill)
)

class HollowRectangle(width: Int, height: Int, color: Color) extends TextureAppearence(
  simplePixmapOperation(width, height, color, _.drawRectangle(0, 0, width, height))
)

class SolidCircle(radius: Int, color: Color) extends TextureAppearence(
  simplePixmapOperation(radius * 2, radius * 2, color, _.fillCircle(radius, radius, radius))
)

class HollowCircle(radius: Int, color: Color) extends TextureAppearence(
  simplePixmapOperation(radius * 2, radius * 2, color, _.drawCircle(radius, radius, radius))
)

class SolidTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) extends TextureAppearence({
  val points = List(p1, p2, p3)
  val pixmapWidth = points.maxBy(_._1)._1
  val pixmapHeight = points.maxBy(_._2)._2
  val operation: Pixmap => Unit = _.fillTriangle(p1._1, p1._2, p2._1, p2._2, p3._1, p3._2)
  simplePixmapOperation(pixmapWidth, pixmapHeight, color, operation)
})

class HollowTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) extends TextureAppearence({
  val points = List(p1, p2, p3)
  val pixmapWidth = points.maxBy(_._1)._1
  val pixmapHeight = points.maxBy(_._2)._2
  val operation: Pixmap => Unit = { pixmap =>
    pixmap.drawLine(p1._1, p1._2, p2._1, p2._2)
    pixmap.drawLine(p1._1, p1._2, p3._1, p3._2)
    pixmap.drawLine(p2._1, p2._2, p3._1, p3._2)
  }
  simplePixmapOperation(pixmapWidth, pixmapHeight, color, operation)
})

class Line(p: (Int, Int), color: Color) extends TextureAppearence(
  simplePixmapOperation(p._1, p._2, color, _.drawLine(0, 0, p._1, p._2))
)

object AppearenceDSL {

  var defaultPixmapFormat = Pixmap.Format.RGBA8888

  implicit def textureToAppearence(texture: Texture): Appearence = Texture(texture)

  implicit def textureRegionToAppearence(textureRegion: TextureRegion): Appearence = Texture(textureRegion)

  implicit def pixmapToAppearence(pixmap: Pixmap): Appearence = Texture(pixmap)

  def Texture(textureRegion: TextureRegion): Appearence = new TextureRegionAppearence(textureRegion)

  def Texture(texture: Texture): Appearence = new TextureAppearence(texture)

  def Texture(pixmap: Pixmap): Appearence = new TextureAppearence(new Texture(pixmap))

  def Invisible: Appearence = InvisibleAppearence

  def pixmapOperation(pixmapWidth: Int, pixmapHeight: Int, op: Pixmap => Unit): Texture = {
    val pixmap = new Pixmap(pixmapWidth, pixmapHeight, defaultPixmapFormat)
    op(pixmap)
    val texture = new Texture(pixmap)
    pixmap.dispose
    texture
  }

  def simplePixmapOperation(pixmapWidth: Int, pixmapHeight: Int, color: Color, op: Pixmap => Unit) =
    pixmapOperation(pixmapWidth, pixmapHeight, { pixmap =>
      pixmap.setColor(color)
      op(pixmap)
    })

  def SolidRectangle(width: Int, height: Int, color: Color) = new SolidRectangle(width: Int, height: Int, color: Color)

  def HollowRectangle(width: Int, height: Int, color: Color) = new HollowRectangle(width: Int, height: Int, color: Color)

  def SolidCircle(radius: Int, color: Color) = new SolidCircle(radius: Int, color: Color)

  def HollowCircle(radius: Int, color: Color) = new HollowCircle(radius: Int, color: Color)

  def SolidTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) =
    new SolidTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color)

  def HollowTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color) =
    new HollowTriangle(p1: (Int, Int), p2: (Int, Int), p3: (Int, Int), color: Color)

  def Line(p: (Int, Int), color: Color) = new Line(p: (Int, Int), color: Color)

}
