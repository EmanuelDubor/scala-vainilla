package edu.unq.vainilla.arkanoid

import edu.unq.vainilla.core.configuration.{ApplicationConfiguration, ApplicationConfigurator}

class GameConfigurator extends ApplicationConfigurator {
  override def configure(config: ApplicationConfiguration): Unit = {
    config.title = "Arkanoid Demo"
  }
}
