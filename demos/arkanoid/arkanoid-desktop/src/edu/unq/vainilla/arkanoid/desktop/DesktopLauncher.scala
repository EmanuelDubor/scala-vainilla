package edu.unq.vainilla.arkanoid.desktop

import edu.unq.vainilla.arkanoid.{ArkanoidDemo, GameConfigurator}
import edu.unq.vainilla.desktop.VanillaEngine


object DesktopLauncher {

  def main(args: Array[String]) {
    VanillaEngine.mainScene = new ArkanoidDemo
    VanillaEngine.configurators += new GameConfigurator
    VanillaEngine.configurators += new DesktopConfigurator

    VanillaEngine.start
  }

}
