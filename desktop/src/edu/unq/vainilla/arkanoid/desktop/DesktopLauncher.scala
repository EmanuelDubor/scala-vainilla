package edu.unq.vainilla.arkanoid.desktop

import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import edu.unq.vainilla.arkanoid.ArkanoidDemo

object DesktopLauncher {

  def main(args: Array[String]) {
    val config = new LwjglApplicationConfiguration
    new LwjglApplication(new ArkanoidDemo, config)
  }


}
