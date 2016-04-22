package edu.unq.vainilla.arkanoid

import edu.unq.vainilla.core.configuration.{Configuration, Configurator}

class GameConfigurator extends Configurator {
  def configure(config: Configuration): Configuration = {
    config.title = "Arkanoid Demo"
    config
  }
}
