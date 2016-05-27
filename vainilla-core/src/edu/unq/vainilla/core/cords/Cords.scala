package edu.unq.vainilla.core.cords

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3

object Cords {

  case class Cord2d(x: Float, y: Float) extends Tuple2(x, y)

  object Cord2d {
    def apply(vector3: Vector3): Cord2d = new Cord2d(vector3.x, vector3.y)
  }

  case class ScreenCord(screenX: Int, screenY: Int) extends Cord2d(screenX.toFloat, screenY.toFloat) {
    def localize(implicit camera: OrthographicCamera): Cord2d = Cord2d(camera.unproject(new Vector3(screenX, screenY, 0)))
  }

}






