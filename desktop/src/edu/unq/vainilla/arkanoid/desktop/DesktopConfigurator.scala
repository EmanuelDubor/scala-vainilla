package edu.unq.vainilla.arkanoid.desktop

import edu.unq.vainilla.core.configuration.{ApplicationConfiguration, ApplicationConfigurator}

class DesktopConfigurator extends ApplicationConfigurator {
  override def configure(config: ApplicationConfiguration): Unit = {
    config.width = 800
    config.height = 600
  }
}
