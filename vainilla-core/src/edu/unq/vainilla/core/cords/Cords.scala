package edu.unq.vainilla.core.cords

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.{Vector2, Vector3}

trait Cord {
  def x: Float

  def y: Float
}

object CordImplicits {
  implicit def cordToTuple(cord: Cord): (Float, Float) = (cord.x, cord.y)

  implicit def tupleNumberToCord2d[A, B](t: (A, B))(implicit a: Numeric[A], b: Numeric[B]): Cord2d = Cord2d(a.toFloat(t._1), b.toFloat(t._2))

  implicit def tupleNumberToScreenCord[A, B](t: (A, B))(implicit a: Numeric[A], b: Numeric[B]): ScreenCord = ScreenCord(a.toFloat(t._1), b.toFloat(t._2))

  implicit def cordToVector2(cord: Cord): Vector2 = new Vector2(cord.x, cord.y)

  implicit def vector2ToCord2d(vector2: Vector2): Cord2d = Cord2d(vector2)

  implicit def vector2ToScreenCord(vector2: Vector2): ScreenCord = ScreenCord(vector2)

  implicit def screenCordToCord(screenCord: ScreenCord)(implicit camera: OrthographicCamera): Cord2d = screenCord.localize

}

case class Cord2d(x: Float, y: Float) extends Cord

object Cord2d {
  def apply(vector3: Vector3): Cord2d = Cord2d(vector3.x, vector3.y)

  def apply(vector2: Vector2): Cord2d = Cord2d(vector2.x, vector2.y)
}

case class ScreenCord(x: Float, y: Float) extends Cord {
  def localize(implicit camera: OrthographicCamera): Cord2d = Cord2d(camera.unproject(new Vector3(x, y, 0)))
}

object ScreenCord extends {
  def apply(vector3: Vector3): ScreenCord = ScreenCord(vector3.x, vector3.y)

  def apply(vector2: Vector2): ScreenCord = ScreenCord(vector2.x, vector2.y)
}







